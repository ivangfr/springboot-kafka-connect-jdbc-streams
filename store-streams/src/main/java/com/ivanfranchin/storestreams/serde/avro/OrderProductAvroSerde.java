package com.ivanfranchin.storestreams.serde.avro;

import com.ivanfranchin.commons.storeapp.avro.OrderProduct;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;

public class OrderProductAvroSerde extends SpecificAvroSerde<OrderProduct> {
}
