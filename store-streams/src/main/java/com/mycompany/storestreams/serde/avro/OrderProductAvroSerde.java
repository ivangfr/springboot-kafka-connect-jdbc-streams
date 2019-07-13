package com.mycompany.storestreams.serde.avro;

import com.mycompany.commons.storeapp.avro.OrderProduct;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;

public class OrderProductAvroSerde extends SpecificAvroSerde<OrderProduct> {
}
