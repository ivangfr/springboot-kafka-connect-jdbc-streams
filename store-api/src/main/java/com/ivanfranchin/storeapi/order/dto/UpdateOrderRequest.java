package com.ivanfranchin.storeapi.order.dto;

import com.ivanfranchin.storeapi.order.model.OrderStatus;
import com.ivanfranchin.storeapi.order.model.PaymentType;
import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateOrderRequest(
        @Schema(example = "CASH") PaymentType paymentType,
        @Schema(example = "PAYED") OrderStatus status) {
}
