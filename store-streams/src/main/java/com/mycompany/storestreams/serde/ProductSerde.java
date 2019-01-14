package com.mycompany.storestreams.serde;

import com.mycompany.storestreams.event.Product;
import org.springframework.kafka.support.serializer.JsonSerde;

public class ProductSerde extends JsonSerde<Product> {
}
