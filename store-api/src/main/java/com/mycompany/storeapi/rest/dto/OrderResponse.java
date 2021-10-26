package com.mycompany.storeapi.rest.dto;

import com.mycompany.storeapi.model.OrderStatus;
import com.mycompany.storeapi.model.PaymentType;
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
