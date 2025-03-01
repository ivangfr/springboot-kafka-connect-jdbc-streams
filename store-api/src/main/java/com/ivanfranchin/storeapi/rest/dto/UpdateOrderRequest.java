package com.ivanfranchin.storeapi.rest.dto;

import com.ivanfranchin.storeapi.model.OrderStatus;
import com.ivanfranchin.storeapi.model.PaymentType;
import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateOrderRequest(
        @Schema(example = "CASH") PaymentType paymentType,
        @Schema(example = "PAYED") OrderStatus status) {
}
