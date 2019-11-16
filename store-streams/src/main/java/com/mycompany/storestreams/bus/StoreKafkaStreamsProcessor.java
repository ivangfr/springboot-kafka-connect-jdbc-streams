package com.mycompany.storestreams.bus;

import com.mycompany.commons.storeapp.events.Customer;
import com.mycompany.commons.storeapp.events.Order;
import com.mycompany.commons.storeapp.events.OrderDetailed;
import com.mycompany.commons.storeapp.events.OrderProduct;
import com.mycompany.commons.storeapp.events.Product;
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
    KTable<String, Customer> customerInput();

    @Input(PRODUCT_INPUT)
    KTable<String, Product> productInput();

    @Input(ORDER_INPUT)
    KStream<String, Order> orderInput();

    @Input(ORDER_PRODUCT_INPUT)
    KStream<String, OrderProduct> orderProductInput();

    @Output(ORDER_OUTPUT)
    KStream<String, OrderDetailed> orderOutput();

}
