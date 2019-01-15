package com.mycompany.storestreams.serde.json;

import com.mycompany.storestreams.event.Product;
import org.springframework.kafka.support.serializer.JsonSerde;

public class ProductJsonSerde extends JsonSerde<Product> {
}
