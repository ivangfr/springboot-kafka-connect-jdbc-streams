package com.ivanfranchin.storestreams.serde.json;

import com.ivanfranchin.commons.storeapp.json.Customer;
import org.springframework.kafka.support.serializer.JacksonJsonSerde;

public class CustomerJsonSerde extends JacksonJsonSerde<Customer> {
}
