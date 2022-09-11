package com.ivanfranchin.storeapi.rest.dto;

import com.ivanfranchin.storeapi.model.OrderStatus;
import com.ivanfranchin.storeapi.model.PaymentType;

import java.util.List;

public record OrderResponse(String id, Long customerId, PaymentType paymentType, OrderStatus status,
                            List<ProductResponse> products) {

    public record ProductResponse(Long id, Integer unit) {
    }
}
