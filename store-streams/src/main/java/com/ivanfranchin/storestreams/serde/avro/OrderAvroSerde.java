package com.ivanfranchin.storestreams.serde.avro;

import com.ivanfranchin.commons.storeapp.avro.Order;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;

public class OrderAvroSerde extends SpecificAvroSerde<Order> {
}
