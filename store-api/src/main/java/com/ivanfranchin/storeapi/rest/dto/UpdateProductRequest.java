package com.ivanfranchin.storeapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UpdateProductRequest(
        @Schema(example = "MacBook Air") String name,
        @Schema(example = "2450") @Positive BigDecimal price) {
}
