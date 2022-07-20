package com.ivanfranchin.storeapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
