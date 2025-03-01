package com.ivanfranchin.storeapi.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AddCustomerRequest(
        @Schema(example = "Ivan Franchin") @NotBlank String name,
        @Schema(example = "ivan.franchin@test.com") @NotBlank @Email String email,
        @Schema(example = "Street Brooklyn 123") @NotBlank String address,
        @Schema(example = "445566") @NotBlank String phone) {
}
