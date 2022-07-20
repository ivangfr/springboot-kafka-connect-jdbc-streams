package com.ivanfranchin.storeapi.rest.dto;

import com.ivanfranchin.storeapi.model.OrderStatus;
import com.ivanfranchin.storeapi.model.PaymentType;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {

    private String id;
    private Long customerId;
    private PaymentType paymentType;
    private OrderStatus status;
    private List<ProductResponse> products;

    @Data
    public static final class ProductResponse {
        private Long id;
        private Integer unit;
    }
}
