package com.mycompany.storeapi.rest.dto;

import com.mycompany.storeapi.model.OrderStatus;
import com.mycompany.storeapi.model.PaymentType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
public class CreateOrderDto {

    @ApiModelProperty(example = "1")
    @NotNull
    private Long customerId;

    @ApiModelProperty(position = 2, example = "BITCOIN")
    @NotNull
    private PaymentType paymentType;

    @ApiModelProperty(position = 3, example = "OPEN")
    @NotNull
    private OrderStatus status;

    @ApiModelProperty(position = 4)
    @Valid
    private List<CreateOrderProductDto> products;

    @Data
    public static class CreateOrderProductDto {

        @ApiModelProperty(example = "15")
        @NotNull
        private Long id;

        @ApiModelProperty(position = 2, example = "1")
        @NotNull
        @Positive
        private Integer unit;

    }

}
