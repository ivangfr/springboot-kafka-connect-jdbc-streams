package com.mycompany.storestreams.bus;

import com.mycompany.commons.storeapp.avro.Customer;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface StoreKafkaStreamsProcessor {

    String CUSTOMER_INPUT = "customer-input";
//    String OUTPUT = "output";

    @Input(CUSTOMER_INPUT)
    KStream<Long, Customer> customerInput();

//    @Output(OUTPUT)
//    KStream<?, ?> output();

}
