package com.mycompany.storestreams.bus;

import com.mycompany.commons.storeapp.avro.Customer;
import com.mycompany.commons.storeapp.avro.Order;
import com.mycompany.commons.storeapp.avro.OrderDetailed;
import com.mycompany.commons.storeapp.avro.OrderProduct;
import com.mycompany.commons.storeapp.avro.Product;
import com.mycompany.commons.storeapp.avro.ProductDetail;
import com.mycompany.commons.storeapp.avro.ProductDetailList;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroDeserializer;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
@Profile("avro")
public class StoreStreamsAvro {

    @Value("${spring.cloud.stream.kafka.streams.binder.configuration.schema.registry.url}")
    private String schemaRegistryUrl;

    private Serde<ProductDetailList> productDetailListSerde;
    private Serde<OrderDetailed> orderDetailedSerde;

    @PostConstruct
    public void init() {
        Map<String, String> serdeConfig = Collections.singletonMap(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);

        SpecificAvroSerializer<ProductDetailList> setSerializer = new SpecificAvroSerializer<>();
        SpecificAvroDeserializer<ProductDetailList> setDeserializer = new SpecificAvroDeserializer<>();
        productDetailListSerde = Serdes.serdeFrom(setSerializer, setDeserializer);
        productDetailListSerde.configure(serdeConfig, false);

        SpecificAvroSerializer<OrderDetailed> orderDetailedSerializer = new SpecificAvroSerializer<>();
        SpecificAvroDeserializer<OrderDetailed> orderDetailedDeserializer = new SpecificAvroDeserializer<>();
        orderDetailedSerde = Serdes.serdeFrom(orderDetailedSerializer, orderDetailedDeserializer);
        orderDetailedSerde.configure(serdeConfig, false);
    }

    @Bean
    public Function<KStream<String, Order>,
            Function<GlobalKTable<String, Customer>,
                    Function<KStream<String, OrderProduct>,
                            Function<GlobalKTable<String, Product>,
                                    KStream<String, OrderDetailed>>>>> process() {
        return orderKStream -> (
                customerGlobalKTable -> (
                        orderProductKStream -> (
                                productGlobalKTable -> (
                                        orderKStream
                                                .peek(this::logKeyValue)
                                                .join(
                                                        customerGlobalKTable,
                                                        (s, order) -> String.valueOf(order.getCustomerId()),
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
                                                                        ProductDetailList::new,
                                                                        (key, productDetail, productDetailList) -> addProductDetail(productDetail, productDetailList),
                                                                        Materialized.with(Serdes.String(), productDetailListSerde)
                                                                )
                                                                .toStream()
                                                                .peek(this::logKeyValue),
                                                        (orderDetailed, productDetailList) -> setProductDetailList(productDetailList, orderDetailed),
                                                        JoinWindows.of(Duration.ofMinutes(1)),
                                                        StreamJoined.with(Serdes.String(), orderDetailedSerde, productDetailListSerde)
                                                )
                                                .peek(this::logKeyValue)
                                )
                        )
                )
        );
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

    private ProductDetailList addProductDetail(ProductDetail productDetail, ProductDetailList productDetailList) {
        List<ProductDetail> products = productDetailList.getProducts();
        if (products == null) {
            products = new LinkedList<>();
            productDetailList.setProducts(products);
        }
        products.add(productDetail);
        return productDetailList;
    }

    private OrderDetailed setProductDetailList(ProductDetailList productDetailList, OrderDetailed orderDetailed) {
        orderDetailed.setProducts(productDetailList.getProducts());
        return orderDetailed;
    }

    private void logKeyValue(String key, Object value) {
        log.info("==> key: {}, value: {}", key, value);
    }
}
