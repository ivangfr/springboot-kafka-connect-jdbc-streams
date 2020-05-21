package com.mycompany.storestreams.bus;

import com.mycompany.commons.storeapp.events.Customer;
import com.mycompany.commons.storeapp.events.Order;
import com.mycompany.commons.storeapp.events.OrderDetailed;
import com.mycompany.commons.storeapp.events.OrderProduct;
import com.mycompany.commons.storeapp.events.OrderProductDetail;
import com.mycompany.commons.storeapp.events.Product;
import com.mycompany.commons.storeapp.events.ProductDetail;
import com.mycompany.storestreams.util.JsonSerdeFactory;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.Set;

@Slf4j
@Component
@Profile("!avro")
@EnableBinding(StoreKafkaStreamsProcessor.class)
public class StoreStreamsJson {

    private final MapperFacade mapperFacade;

    public StoreStreamsJson(MapperFacade mapperFacade) {
        this.mapperFacade = mapperFacade;
    }

    @StreamListener
    @SendTo(StoreKafkaStreamsProcessor.ORDER_OUTPUT)
    public KStream<String, OrderDetailed> process(
            @Input(StoreKafkaStreamsProcessor.CUSTOMER_INPUT) KTable<String, Customer> customerKTable,
            @Input(StoreKafkaStreamsProcessor.PRODUCT_INPUT) KTable<String, Product> productKTable,
            @Input(StoreKafkaStreamsProcessor.ORDER_INPUT) KStream<String, Order> orderIdKeyOrderValueKStream,
            @Input(StoreKafkaStreamsProcessor.ORDER_PRODUCT_INPUT) KStream<String, OrderProduct> orderIdKeyOrderProductValueKStream) {

        //--
        // Useful for logging
        //
        customerKTable.toStream().foreach(this::logKeyValue);
        productKTable.toStream().foreach(this::logKeyValue);
        orderIdKeyOrderValueKStream.foreach(this::logKeyValue);
        orderIdKeyOrderProductValueKStream.foreach(this::logKeyValue);

        // --
        // Add customer info to OrderDetailed

        KStream<String, Order> customerIdKeyOrderValueKStream = orderIdKeyOrderValueKStream
                .map((key, order) -> new KeyValue<>(order.getCustomer_id().toString(), order));

        KStream<String, OrderDetailed> customerIdKeyOrderDetailedValueKStream = customerIdKeyOrderValueKStream
                .join(customerKTable, (order, customer) -> {
                    OrderDetailed orderDetailed = mapperFacade.map(order, OrderDetailed.class);
                    orderDetailed.setCustomer_name(customer.getName());
                    return orderDetailed;
                }, Joined.with(Serdes.String(), JsonSerdeFactory.orderSerde, JsonSerdeFactory.customerSerde));

        KStream<String, OrderDetailed> orderIdKeyOrderDetailedWithoutProductsSetValueKStream = customerIdKeyOrderDetailedValueKStream
                .map((key, orderDetailed) -> new KeyValue<>(orderDetailed.getId(), orderDetailed));

        // --
        // Add product info to OrderProductDetail

        KStream<String, OrderProduct> productIdKeyOrderProductValueKStream = orderIdKeyOrderProductValueKStream
                .map((key, orderProduct) -> new KeyValue<>(orderProduct.getProduct_id().toString(), orderProduct));

        KStream<String, OrderProductDetail> productIdKeyOrderProductDetailValueKStream = productIdKeyOrderProductValueKStream
                .join(productKTable, (orderProduct, product) -> {
                    OrderProductDetail orderProductDetail = mapperFacade.map(orderProduct, OrderProductDetail.class);
                    orderProductDetail.setProduct_name(product.getName());
                    return orderProductDetail;
                }, Joined.with(Serdes.String(), JsonSerdeFactory.orderProductSerde, JsonSerdeFactory.productSerde));

        KStream<String, OrderProductDetail> orderIdKeyOrderProductDetailValueKStream = productIdKeyOrderProductDetailValueKStream
                .map((key, orderProductDetail) -> new KeyValue<>(orderProductDetail.getOrder_id(), orderProductDetail));

        // --
        // Extract OrderProductDetail to ProductDetail

        KStream<String, ProductDetail> orderIdKeyProductDetailValueKStream = orderIdKeyOrderProductDetailValueKStream
                .mapValues(orderProductDetail -> mapperFacade.map(orderProductDetail, ProductDetail.class));

        // --
        // Aggregate (in a Set) ProductDetail by order

        KTable<String, Set<ProductDetail>> orderIdKeyOrderProductDetailSetValueKTable = orderIdKeyProductDetailValueKStream
                .groupByKey(Grouped.with(Serdes.String(), JsonSerdeFactory.productDetailSerde))
                .aggregate(LinkedHashSet::new,
                        (key, productDetail, productDetails) -> {
                            productDetails.add(productDetail);
                            return productDetails;
                        }, Materialized.with(Serdes.String(), JsonSerdeFactory.setProductDetailSerde));

        // --
        // Stream ProductDetail Set by order

        KStream<String, Set<ProductDetail>> orderIdKeyOrderProductDetailSetValueKStream = orderIdKeyOrderProductDetailSetValueKTable.toStream();

        // --
        // Add ProductDetail Set to OrderDetailed

        KStream<String, OrderDetailed> orderIdKeyOrderDetailedValueKStream = orderIdKeyOrderDetailedWithoutProductsSetValueKStream
                .leftJoin(orderIdKeyOrderProductDetailSetValueKStream,
                        (orderDetailed, products) -> {
                            orderDetailed.setProducts(products);
                            return orderDetailed;
                        }, JoinWindows.of(Duration.ofHours(1)), StreamJoined.with(Serdes.String(), JsonSerdeFactory.orderDetailedSerde, JsonSerdeFactory.setProductDetailSerde));

        orderIdKeyOrderDetailedValueKStream.foreach(this::logKeyValue);

        return orderIdKeyOrderDetailedValueKStream;
    }

    private void logKeyValue(String key, Object value) {
        log.info("==> key: {}, value: {}", key, value);
    }

}
