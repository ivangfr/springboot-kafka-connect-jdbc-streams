package com.mycompany.storestreams.bus;

import com.mycompany.commons.storeapp.avro.Customer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableBinding(StoreKafkaStreamsProcessor.class)
public class StoreStream {

    @StreamListener
//    @SendTo("output")
    public /*KStream<?, ?>*/ void process(
            @Input(StoreKafkaStreamsProcessor.CUSTOMER_INPUT) KStream<Long, Customer> customerKStream) {

        customerKStream.foreach((key, value) -> log.info("key: {}, value: {}", key, value));

    }

}
