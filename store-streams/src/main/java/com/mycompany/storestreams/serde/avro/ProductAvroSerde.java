package com.mycompany.storestreams.serde.avro;

import com.mycompany.commons.storeapp.avro.Product;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;

public class ProductAvroSerde extends SpecificAvroSerde<Product> {
}
