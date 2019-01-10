package com.mycompany.storekafkastreams.bus;

import com.mycompany.commom.storeapp.avro.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.kafka.streams.annotations.KafkaStreamsProcessor;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableBinding(KafkaStreamsProcessor.class)
public class StoreStream {

    @StreamListener
    @SendTo("output")
    public KStream<String, String> process(@Input("input") KStream<String, Product> events) {
        log.info("----- Entrou");

//        events.foreach(new ForeachAction<String, Customer>() {
//            @Override
//            public void apply(String s, Customer customer) {
//                log.info("s = {}, customer = {}", s, customer);
//            }
//        });

        return events.map(new KeyValueMapper<String, Product, KeyValue<? extends String, ? extends String>>() {
            @Override
            public KeyValue<? extends String, ? extends String> apply(String s, Product product) {
                return new KeyValue<>(s, product.getName().toString());
            }
        });
    }

}
