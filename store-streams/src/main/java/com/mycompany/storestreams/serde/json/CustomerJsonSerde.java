package com.mycompany.storestreams.serde.json;

import com.mycompany.commons.storeapp.events.Customer;
import org.springframework.kafka.support.serializer.JsonSerde;

public class CustomerJsonSerde extends JsonSerde<Customer> {
}
