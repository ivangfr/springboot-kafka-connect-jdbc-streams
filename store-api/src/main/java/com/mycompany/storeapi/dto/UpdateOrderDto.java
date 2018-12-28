package com.mycompany.storeapi.dto;

import com.mycompany.storeapi.model.OrderStatus;
import com.mycompany.storeapi.model.PaymentType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateOrderDto {

    @ApiModelProperty(example = "CASH")
    private PaymentType paymentType;

    @ApiModelProperty(position = 2, example = "PAYED")
    private OrderStatus status;

}
