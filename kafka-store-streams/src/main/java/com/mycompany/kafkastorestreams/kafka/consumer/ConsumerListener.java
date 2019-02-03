package com.mycompany.kafkastorestreams.kafka.consumer;

import com.mycompany.commons.storeapp.avro.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsumerListener {

    @KafkaListener(topics = "${kafka.consumer.topic}", groupId = "kafkaStoreStreamsGroup")
    public void receive(Message<Order> message) {
        log.info("Received {}", message);
    }

}
