package com.ivanfranchin.storestreams.serde.json;

import com.ivanfranchin.commons.storeapp.json.OrderDetailed;
import org.springframework.kafka.support.serializer.JsonSerde;

public class OrderDetailedJsonSerde extends JsonSerde<OrderDetailed> {
}
