package com.mycompany.storestreams.serde.avro;

import com.mycompany.commons.storeapp.avro.Customer;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;

public class CustomerAvroSerde extends SpecificAvroSerde<Customer> {
}
