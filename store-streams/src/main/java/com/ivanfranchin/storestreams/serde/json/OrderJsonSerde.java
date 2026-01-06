package com.ivanfranchin.storestreams.serde.json;

import com.ivanfranchin.commons.storeapp.json.Order;
import org.springframework.kafka.support.serializer.JacksonJsonSerde;

public class OrderJsonSerde extends JacksonJsonSerde<Order> {
}
