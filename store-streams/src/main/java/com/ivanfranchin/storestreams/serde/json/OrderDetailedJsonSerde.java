package com.ivanfranchin.storestreams.serde.json;

import com.ivanfranchin.commons.storeapp.json.OrderDetailed;
import org.springframework.kafka.support.serializer.JacksonJsonSerde;

public class OrderDetailedJsonSerde extends JacksonJsonSerde<OrderDetailed> {
}
