package com.mycompany.storestreams.util;

import com.mycompany.commons.storeapp.events.Customer;
import com.mycompany.commons.storeapp.events.Order;
import com.mycompany.commons.storeapp.events.OrderDetailed;
import com.mycompany.commons.storeapp.events.OrderProduct;
import com.mycompany.commons.storeapp.events.Product;
import com.mycompany.commons.storeapp.events.ProductDetail;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Set;

public class JsonSerdeFactory {

    public static final Serde<Customer> customerSerde;
    public static final Serde<Product> productSerde;
    public static final Serde<ProductDetail> productDetailSerde;
    public static final Serde<Order> orderSerde;
    public static final Serde<OrderDetailed> orderDetailedSerde;
    public static final Serde<OrderProduct> orderProductSerde;
    public static final Serde<Set> setSerde;

    private JsonSerdeFactory() {
    }

    static {
        JsonSerializer<Customer> customerSerializer = new JsonSerializer<>();
        JsonDeserializer<Customer> customerDeserializer = new JsonDeserializer<>(Customer.class);
        customerSerde = Serdes.serdeFrom(customerSerializer, customerDeserializer);

        JsonSerializer<Product> productSerializer = new JsonSerializer<>();
        JsonDeserializer<Product> productDeserializer = new JsonDeserializer<>(Product.class);
        productSerde = Serdes.serdeFrom(productSerializer, productDeserializer);

        JsonSerializer<ProductDetail> productDetailSerializer = new JsonSerializer<>();
        JsonDeserializer<ProductDetail> productDetailDeserializer = new JsonDeserializer<>(ProductDetail.class);
        productDetailSerde = Serdes.serdeFrom(productDetailSerializer, productDetailDeserializer);

        JsonSerializer<Order> orderSerializer = new JsonSerializer<>();
        JsonDeserializer<Order> orderDeserializer = new JsonDeserializer<>(Order.class);
        orderSerde = Serdes.serdeFrom(orderSerializer, orderDeserializer);

        JsonSerializer<OrderDetailed> orderDetailedSerializer = new JsonSerializer<>();
        JsonDeserializer<OrderDetailed> orderDetailedDeserializer = new JsonDeserializer<>(OrderDetailed.class);
        orderDetailedSerde = Serdes.serdeFrom(orderDetailedSerializer, orderDetailedDeserializer);

        JsonSerializer<OrderProduct> orderProductSerializer = new JsonSerializer<>();
        JsonDeserializer<OrderProduct> orderProductDeserializer = new JsonDeserializer<>(OrderProduct.class);
        orderProductSerde = Serdes.serdeFrom(orderProductSerializer, orderProductDeserializer);

        JsonSerializer<Set> setSerializer = new JsonSerializer<>();
        JsonDeserializer<Set> setDeserializer = new JsonDeserializer<>(Set.class);
        setSerde = Serdes.serdeFrom(setSerializer, setDeserializer);
    }

}
