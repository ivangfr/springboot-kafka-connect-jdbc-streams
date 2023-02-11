package com.ivanfranchin.storeapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {

    @Schema(example = "MacBook Air")
    private String name;

    @Schema(example = "2450")
    @Positive
    private BigDecimal price;
}
