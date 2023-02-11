package com.ivanfranchin.storeapi.rest.dto;

import com.ivanfranchin.storeapi.model.OrderStatus;
import com.ivanfranchin.storeapi.model.PaymentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {

    @Schema(example = "1")
    @NotNull
    private Long customerId;

    @Schema(example = "BITCOIN")
    @NotNull
    private PaymentType paymentType;

    @Schema(example = "OPEN")
    @NotNull
    private OrderStatus status;

    @Valid
    private List<CreateOrderProductRequest> products;

    @Data
    public static class CreateOrderProductRequest {

        @Schema(example = "15")
        @NotNull
        private Long id;

        @Schema(example = "1")
        @NotNull
        @Positive
        private Integer unit;
    }
}
