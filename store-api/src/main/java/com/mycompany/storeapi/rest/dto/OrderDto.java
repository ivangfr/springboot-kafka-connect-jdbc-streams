package com.mycompany.storeapi.rest.dto;

import com.mycompany.storeapi.model.OrderStatus;
import com.mycompany.storeapi.model.PaymentType;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {

    private String id;
    private Long customerId;
    private PaymentType paymentType;
    private OrderStatus status;
    private List<ProductIdUnit> products;

    @Data
    public static final class ProductIdUnit {
        private Long id;
        private Integer unit;
    }

}
