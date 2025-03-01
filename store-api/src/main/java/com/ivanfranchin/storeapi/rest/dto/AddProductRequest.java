package com.ivanfranchin.storeapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AddProductRequest(
        @Schema(example = "MacBook Pro") @NotBlank String name,
        @Schema(example = "2500") @NotNull @Positive BigDecimal price) {
}
