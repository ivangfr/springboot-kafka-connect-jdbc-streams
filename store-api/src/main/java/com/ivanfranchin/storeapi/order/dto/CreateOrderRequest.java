package com.ivanfranchin.storeapi.order.dto;

import com.ivanfranchin.storeapi.order.model.OrderStatus;
import com.ivanfranchin.storeapi.order.model.PaymentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record CreateOrderRequest(
        @Schema(example = "1") @NotNull Long customerId,
        @Schema(example = "BITCOIN") @NotNull PaymentType paymentType,
        @Schema(example = "OPEN") @NotNull OrderStatus status,
        @Valid @NotNull @NotEmpty List<CreateOrderProductRequest> products) {

    public record CreateOrderProductRequest(
            @Schema(example = "15") @NotNull Long id,
            @Schema(example = "1") @NotNull @Positive Integer unit) {
    }
}
