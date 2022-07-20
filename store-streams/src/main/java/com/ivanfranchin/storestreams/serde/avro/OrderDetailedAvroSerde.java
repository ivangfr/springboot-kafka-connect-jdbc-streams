package com.ivanfranchin.storestreams.serde.avro;

import com.ivanfranchin.commons.storeapp.avro.OrderDetailed;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;

public class OrderDetailedAvroSerde extends SpecificAvroSerde<OrderDetailed> {
}
