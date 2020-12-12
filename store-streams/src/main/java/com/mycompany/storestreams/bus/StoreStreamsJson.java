package com.mycompany.storestreams.bus;

import com.mycompany.commons.storeapp.json.Customer;
import com.mycompany.commons.storeapp.json.Order;
import com.mycompany.commons.storeapp.json.OrderDetailed;
import com.mycompany.commons.storeapp.json.OrderProduct;
import com.mycompany.commons.storeapp.json.Product;
import com.mycompany.commons.storeapp.json.ProductDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
@Profile("!avro")
@EnableBinding(StoreKafkaStreamsProcessor.class)
public class StoreStreamsJson {

    public static final Serde<List<ProductDetail>> productDetailListSerde;
    public static final Serde<OrderDetailed> orderDetailedSerde;

    static {
        JsonSerializer<List<ProductDetail>> setSerializer = new JsonSerializer<>();
        JsonDeserializer<List<ProductDetail>> setDeserializer = new JsonDeserializer<>(List.class);
        productDetailListSerde = Serdes.serdeFrom(setSerializer, setDeserializer);

        JsonSerializer<OrderDetailed> orderDetailedSerializer = new JsonSerializer<>();
        JsonDeserializer<OrderDetailed> orderDetailedDeserializer = new JsonDeserializer<>(OrderDetailed.class);
        orderDetailedSerde = Serdes.serdeFrom(orderDetailedSerializer, orderDetailedDeserializer);
    }

    @StreamListener
    @SendTo(StoreKafkaStreamsProcessor.ORDER_OUTPUT)
    public KStream<String, OrderDetailed> process(
            @Input(StoreKafkaStreamsProcessor.CUSTOMER_INPUT) GlobalKTable<String, Customer> customerGlobalKTable,
            @Input(StoreKafkaStreamsProcessor.PRODUCT_INPUT) GlobalKTable<String, Product> productGlobalKTable,
            @Input(StoreKafkaStreamsProcessor.ORDER_INPUT) KStream<String, Order> orderKStream,
            @Input(StoreKafkaStreamsProcessor.ORDER_PRODUCT_INPUT) KStream<String, OrderProduct> orderProductKStream) {

        return orderKStream
                .peek(this::logKeyValue)
                .join(
                        customerGlobalKTable,
                        (s, order) -> order.getCustomerId().toString(),
                        this::toOrderDetailed
                )
                .join(
                        orderProductKStream
                                .peek(this::logKeyValue)
                                .join(
                                        productGlobalKTable,
                                        (s, orderProduct) -> String.valueOf(orderProduct.getProductId()),
                                        this::toProductDetail
                                )
                                .groupByKey()
                                .aggregate(
                                        LinkedList::new,
                                        (key, productDetail, productDetailList) -> addProductDetail(productDetail, productDetailList),
                                        Materialized.with(Serdes.String(), productDetailListSerde)
                                )
                                .toStream()
                                .peek(this::logKeyValue),
                        (orderDetailed, productDetailList) -> setProductDetailList(productDetailList, orderDetailed),
                        JoinWindows.of(Duration.ofMinutes(1)),
                        StreamJoined.with(Serdes.String(), orderDetailedSerde, productDetailListSerde)
                )
                .peek(this::logKeyValue);
    }

    private OrderDetailed toOrderDetailed(Order order, Customer customer) {
        OrderDetailed orderDetailed = new OrderDetailed();
        orderDetailed.setId(order.getId());
        orderDetailed.setCustomerId(order.getCustomerId());
        orderDetailed.setCustomerName(customer.getName());
        orderDetailed.setStatus(order.getStatus());
        orderDetailed.setPaymentType(order.getPaymentType());
        orderDetailed.setCreatedAt(order.getCreatedAt());
        orderDetailed.setProducts(Collections.emptyList());
        return orderDetailed;
    }

    private ProductDetail toProductDetail(OrderProduct orderProduct, Product product) {
        ProductDetail productDetail = new ProductDetail();
        productDetail.setId(orderProduct.getProductId());
        productDetail.setName(product.getName());
        productDetail.setPrice(product.getPrice());
        productDetail.setUnit(orderProduct.getUnit());
        return productDetail;
    }

    private List<ProductDetail> addProductDetail(ProductDetail productDetail, List<ProductDetail> productDetailList) {
        productDetailList.add(productDetail);
        return productDetailList;
    }

    private OrderDetailed setProductDetailList(List<ProductDetail> productDetailList, OrderDetailed orderDetailed) {
        orderDetailed.setProducts(productDetailList);
        return orderDetailed;
    }

    private void logKeyValue(String key, Object value) {
        log.info("==> key: {}, value: {}", key, value);
    }

}
