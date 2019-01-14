package com.mycompany.storestreams.serde;

import com.mycompany.storestreams.event.Customer;
import org.springframework.kafka.support.serializer.JsonSerde;

public class CustomerSerde extends JsonSerde<Customer> {
}
