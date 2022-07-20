package com.ivanfranchin.storestreams.serde.json;

import com.ivanfranchin.commons.storeapp.json.OrderProduct;
import org.springframework.kafka.support.serializer.JsonSerde;

public class OrderProductJsonSerde extends JsonSerde<OrderProduct> {
}
