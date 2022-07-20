package com.ivanfranchin.storestreams.serde.avro;

import com.ivanfranchin.commons.storeapp.avro.Customer;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;

public class CustomerAvroSerde extends SpecificAvroSerde<Customer> {
}
