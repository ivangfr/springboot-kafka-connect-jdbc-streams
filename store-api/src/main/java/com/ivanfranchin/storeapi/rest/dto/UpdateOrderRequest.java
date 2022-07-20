package com.ivanfranchin.storeapi.rest.dto;

import com.ivanfranchin.storeapi.model.OrderStatus;
import com.ivanfranchin.storeapi.model.PaymentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateOrderRequest {

    @Schema(example = "CASH")
    private PaymentType paymentType;

    @Schema(example = "PAYED")
    private OrderStatus status;
}
