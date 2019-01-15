package com.mycompany.storestreams.serde.avro;

import com.mycompany.commons.storeapp.avro.Order;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;

public class OrderAvroSerde extends SpecificAvroSerde<Order> {
}
