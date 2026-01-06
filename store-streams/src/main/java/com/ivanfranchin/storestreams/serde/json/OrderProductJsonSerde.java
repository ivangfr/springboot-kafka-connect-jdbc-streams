package com.ivanfranchin.storestreams.serde.json;

import com.ivanfranchin.commons.storeapp.json.OrderProduct;
import org.springframework.kafka.support.serializer.JacksonJsonSerde;

public class OrderProductJsonSerde extends JacksonJsonSerde<OrderProduct> {
}
