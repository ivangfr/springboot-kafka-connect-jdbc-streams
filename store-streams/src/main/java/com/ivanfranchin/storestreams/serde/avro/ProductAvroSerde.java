package com.ivanfranchin.storestreams.serde.avro;

import com.ivanfranchin.commons.storeapp.avro.Product;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;

public class ProductAvroSerde extends SpecificAvroSerde<Product> {
}
