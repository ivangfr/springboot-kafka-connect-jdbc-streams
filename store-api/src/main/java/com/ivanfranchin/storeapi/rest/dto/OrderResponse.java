package com.ivanfranchin.storeapi.rest.dto;

import com.ivanfranchin.storeapi.model.Order;
import com.ivanfranchin.storeapi.model.OrderStatus;
import com.ivanfranchin.storeapi.model.PaymentType;

import java.util.List;

public record OrderResponse(String id, Long customerId, PaymentType paymentType, OrderStatus status,
                            List<ProductResponse> products) {

    public record ProductResponse(Long id, Integer unit) {
    }

    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomer().getId(),
                order.getPaymentType(),
                order.getStatus(),
                order.getOrderProducts()
                        .stream()
                        .map(op -> new ProductResponse(op.getProduct().getId(), op.getUnit()))
                        .toList()
        );
    }
}
