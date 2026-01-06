package com.ivanfranchin.storestreams.serde.json;

import com.ivanfranchin.commons.storeapp.json.Product;
import org.springframework.kafka.support.serializer.JacksonJsonSerde;

public class ProductJsonSerde extends JacksonJsonSerde<Product> {
}
