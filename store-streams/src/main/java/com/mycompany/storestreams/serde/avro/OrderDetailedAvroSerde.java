package com.mycompany.storestreams.serde.avro;

import com.mycompany.commons.storeapp.avro.OrderDetailed;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;

public class OrderDetailedAvroSerde extends SpecificAvroSerde<OrderDetailed> {
}
