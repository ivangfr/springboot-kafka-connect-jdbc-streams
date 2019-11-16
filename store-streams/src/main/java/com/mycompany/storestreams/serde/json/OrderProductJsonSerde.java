package com.mycompany.storestreams.serde.json;

import com.mycompany.commons.storeapp.events.OrderProduct;
import org.springframework.kafka.support.serializer.JsonSerde;

public class OrderProductJsonSerde extends JsonSerde<OrderProduct> {
}
