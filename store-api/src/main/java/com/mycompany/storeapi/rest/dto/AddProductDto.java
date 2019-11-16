package com.mycompany.storeapi.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class AddProductDto {

    @ApiModelProperty(example = "MacBook Pro")
    @NotBlank
    private String name;

    @ApiModelProperty(position = 1, example = "2500")
    @NotNull
    @Positive
    private BigDecimal price;

}
