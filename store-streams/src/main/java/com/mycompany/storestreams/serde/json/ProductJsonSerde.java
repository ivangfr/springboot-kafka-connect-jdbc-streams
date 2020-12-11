package com.mycompany.storestreams.serde.json;

import com.mycompany.commons.storeapp.json.Product;
import org.springframework.kafka.support.serializer.JsonSerde;

public class ProductJsonSerde extends JsonSerde<Product> {
}
