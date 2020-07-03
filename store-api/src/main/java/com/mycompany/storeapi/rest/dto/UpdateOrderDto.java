package com.mycompany.storeapi.rest.dto;

import com.mycompany.storeapi.model.OrderStatus;
import com.mycompany.storeapi.model.PaymentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateOrderDto {

    @Schema(example = "CASH")
    private PaymentType paymentType;

    @Schema(example = "PAYED")
    private OrderStatus status;

}
