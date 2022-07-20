package com.ivanfranchin.storestreams.serde.json;

import com.ivanfranchin.commons.storeapp.json.Customer;
import org.springframework.kafka.support.serializer.JsonSerde;

public class CustomerJsonSerde extends JsonSerde<Customer> {
}
