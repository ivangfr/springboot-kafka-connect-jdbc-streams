package com.ivanfranchin.storestreams.serde.json;

import com.ivanfranchin.commons.storeapp.json.Order;
import org.springframework.kafka.support.serializer.JsonSerde;

public class OrderJsonSerde extends JsonSerde<Order> {
}
