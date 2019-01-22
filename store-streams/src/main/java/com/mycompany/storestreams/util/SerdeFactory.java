package com.mycompany.storestreams.util;

import com.mycompany.commons.storeapp.events.Customer;
import com.mycompany.commons.storeapp.events.Order;
import com.mycompany.commons.storeapp.events.OrderDetail;
import com.mycompany.commons.storeapp.events.OrderProduct;
import com.mycompany.commons.storeapp.events.Product;
import com.mycompany.commons.storeapp.events.ProductDetail;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Set;

public class SerdeFactory {

    public static final Serde<Customer> customerJsonSerde;
    public static final Serde<Product> productJsonSerde;
    public static final Serde<ProductDetail> productDetailJsonSerde;
    public static final Serde<Order> orderJsonSerde;
    public static final Serde<OrderDetail> orderDetailJsonSerde;
    public static final Serde<OrderProduct> orderProductJsonSerde;
    public static final Serde<Set> setJsonSerde;

    static {
        JsonSerializer<Customer> customerJsonSerializer = new JsonSerializer<>();
        JsonDeserializer<Customer> customerJsonDeserializer = new JsonDeserializer<>(Customer.class);
        customerJsonSerde = Serdes.serdeFrom(customerJsonSerializer, customerJsonDeserializer);

        JsonSerializer<Product> productJsonSerializer = new JsonSerializer<>();
        JsonDeserializer<Product> productJsonDeserializer = new JsonDeserializer<>(Product.class);
        productJsonSerde = Serdes.serdeFrom(productJsonSerializer, productJsonDeserializer);

        JsonSerializer<ProductDetail> productDetailJsonSerializer = new JsonSerializer<>();
        JsonDeserializer<ProductDetail> productDetailJsonDeserializer = new JsonDeserializer<>(ProductDetail.class);
        productDetailJsonSerde = Serdes.serdeFrom(productDetailJsonSerializer, productDetailJsonDeserializer);

        JsonSerializer<Order> orderJsonSerializer = new JsonSerializer<>();
        JsonDeserializer<Order> orderJsonDeserializer = new JsonDeserializer<>(Order.class);
        orderJsonSerde = Serdes.serdeFrom(orderJsonSerializer, orderJsonDeserializer);

        JsonSerializer<OrderDetail> orderDetailJsonSerializer = new JsonSerializer<>();
        JsonDeserializer<OrderDetail> orderDetailJsonDeserializer = new JsonDeserializer<>(OrderDetail.class);
        orderDetailJsonSerde = Serdes.serdeFrom(orderDetailJsonSerializer, orderDetailJsonDeserializer);

        JsonSerializer<OrderProduct> orderProductJsonSerializer = new JsonSerializer<>();
        JsonDeserializer<OrderProduct> orderProductJsonDeserializer = new JsonDeserializer<>(OrderProduct.class);
        orderProductJsonSerde = Serdes.serdeFrom(orderProductJsonSerializer, orderProductJsonDeserializer);

        JsonSerializer<Set> setJsonSerializer = new JsonSerializer<>();
        JsonDeserializer<Set> setJsonDeserializer = new JsonDeserializer<>(Set.class);
        setJsonSerde = Serdes.serdeFrom(setJsonSerializer, setJsonDeserializer);
    }

}
