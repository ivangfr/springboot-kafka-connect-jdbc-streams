package com.mycompany.storestreams.util;

import com.mycompany.storestreams.event.Customer;
import com.mycompany.storestreams.event.Order;
import com.mycompany.storestreams.event.OrderDetail;
import com.mycompany.storestreams.event.OrderProduct;
import com.mycompany.storestreams.event.Product;
import com.mycompany.storestreams.event.ProductDetail;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Set;

public class SerdeFactory {

    public static final Serde<Customer> customerSerde;
    public static final Serde<Product> productSerde;
    public static final Serde<ProductDetail> productDetailSerde;
    public static final Serde<Order> orderSerde;
    public static final Serde<OrderDetail> orderDetailSerde;
    public static final Serde<OrderProduct> orderProductSerde;
    public static final Serde<Set> setSerde;

    static {
        JsonSerializer<Customer> customerJsonSerializer = new JsonSerializer<>();
        JsonDeserializer<Customer> customerJsonDeserializer = new JsonDeserializer<>(Customer.class);
        customerSerde = Serdes.serdeFrom(customerJsonSerializer, customerJsonDeserializer);

        JsonSerializer<Product> productJsonSerializer = new JsonSerializer<>();
        JsonDeserializer<Product> productJsonDeserializer = new JsonDeserializer<>(Product.class);
        productSerde = Serdes.serdeFrom(productJsonSerializer, productJsonDeserializer);

        JsonSerializer<ProductDetail> productDetailJsonSerializer = new JsonSerializer<>();
        JsonDeserializer<ProductDetail> productDetailJsonDeserializer = new JsonDeserializer<>(ProductDetail.class);
        productDetailSerde = Serdes.serdeFrom(productDetailJsonSerializer, productDetailJsonDeserializer);

        JsonSerializer<Order> orderJsonSerializer = new JsonSerializer<>();
        JsonDeserializer<Order> orderJsonDeserializer = new JsonDeserializer<>(Order.class);
        orderSerde = Serdes.serdeFrom(orderJsonSerializer, orderJsonDeserializer);

        JsonSerializer<OrderDetail> orderDetailJsonSerializer = new JsonSerializer<>();
        JsonDeserializer<OrderDetail> orderDetailJsonDeserializer = new JsonDeserializer<>(OrderDetail.class);
        orderDetailSerde = Serdes.serdeFrom(orderDetailJsonSerializer, orderDetailJsonDeserializer);

        JsonSerializer<OrderProduct> orderProductJsonSerializer = new JsonSerializer<>();
        JsonDeserializer<OrderProduct> orderProductJsonDeserializer = new JsonDeserializer<>(OrderProduct.class);
        orderProductSerde = Serdes.serdeFrom(orderProductJsonSerializer, orderProductJsonDeserializer);

        JsonSerializer<Set> setJsonSerializer = new JsonSerializer<>();
        JsonDeserializer<Set> setJsonDeserializer = new JsonDeserializer<>(Set.class);
        setSerde = Serdes.serdeFrom(setJsonSerializer, setJsonDeserializer);
    }

}
