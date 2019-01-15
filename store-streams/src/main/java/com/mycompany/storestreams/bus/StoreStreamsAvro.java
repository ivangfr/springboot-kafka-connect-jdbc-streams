package com.mycompany.storestreams.bus;

import com.mycompany.commons.storeapp.avro.Customer;
import com.mycompany.commons.storeapp.avro.Order;
import com.mycompany.commons.storeapp.avro.OrderProduct;
import com.mycompany.commons.storeapp.avro.Product;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("avroconverter")
@EnableBinding(StoreKafkaStreamsProcessor.class)
public class StoreStreamsAvro {

    private final MapperFacade mapperFacade;

    public StoreStreamsAvro(MapperFacade mapperFacade) {
        this.mapperFacade = mapperFacade;
    }

    @StreamListener
    @SendTo(StoreKafkaStreamsProcessor.ORDER_OUTPUT)
    public void process(
            @Input(StoreKafkaStreamsProcessor.CUSTOMER_INPUT) KTable<String, Customer> customerKTable,
            @Input(StoreKafkaStreamsProcessor.PRODUCT_INPUT) KTable<String, Product> productKTable,
            @Input(StoreKafkaStreamsProcessor.ORDER_INPUT) KStream<String, Order> orderIdKeyOrderValueKStream,
            @Input(StoreKafkaStreamsProcessor.ORDER_PRODUCT_INPUT) KStream<String, OrderProduct> orderIdKeyOrderProductValueKStream) {

        customerKTable.toStream().foreach((key, value) -> log.info("key: {}, value: {}", key, value));
        productKTable.toStream().foreach((key, value) -> log.info("key: {}, value: {}", key, value));
        orderIdKeyOrderValueKStream.foreach((key, value) -> log.info("key: {}, value: {}", key, value));
        orderIdKeyOrderProductValueKStream.foreach((key, value) -> log.info("key: {}, value: {}", key, value));

        //TODO Continue implementation
    }

}
