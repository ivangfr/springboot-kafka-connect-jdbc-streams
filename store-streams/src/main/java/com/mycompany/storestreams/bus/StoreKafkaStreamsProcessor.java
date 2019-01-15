package com.mycompany.storestreams.bus;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface StoreKafkaStreamsProcessor {

    String CUSTOMER_INPUT = "customer-input";
    String PRODUCT_INPUT = "product-input";
    String ORDER_INPUT = "order-input";
    String ORDER_PRODUCT_INPUT = "order-product-input";
    String ORDER_OUTPUT = "order-output";

    @Input(CUSTOMER_INPUT)
    KTable<?, ?> customerInput();

    @Input(PRODUCT_INPUT)
    KTable<?, ?> productInput();

    @Input(ORDER_INPUT)
    KStream<?, ?> orderInput();

    @Input(ORDER_PRODUCT_INPUT)
    KStream<?, ?> orderProductInput();

    @Output(ORDER_OUTPUT)
    KStream<?, ?> orderOutput();

}
