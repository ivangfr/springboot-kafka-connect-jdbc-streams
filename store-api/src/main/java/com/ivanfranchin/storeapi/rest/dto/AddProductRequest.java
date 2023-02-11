package com.ivanfranchin.storeapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductRequest {

    @Schema(example = "MacBook Pro")
    @NotBlank
    private String name;

    @Schema(example = "2500")
    @NotNull
    @Positive
    private BigDecimal price;
}
