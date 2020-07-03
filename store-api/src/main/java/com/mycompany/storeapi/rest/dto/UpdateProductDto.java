package com.mycompany.storeapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class UpdateProductDto {

    @Schema(example = "MacBook Air")
    private String name;

    @Schema(example = "2450")
    @Positive
    private BigDecimal price;

}
