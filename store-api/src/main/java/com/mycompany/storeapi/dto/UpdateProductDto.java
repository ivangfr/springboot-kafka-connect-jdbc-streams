package com.mycompany.storeapi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class UpdateProductDto {

    @ApiModelProperty(example = "Macbook Air")
    private String name;

    @ApiModelProperty(position = 2, example = "2450")
    @Positive
    private BigDecimal price;

}
