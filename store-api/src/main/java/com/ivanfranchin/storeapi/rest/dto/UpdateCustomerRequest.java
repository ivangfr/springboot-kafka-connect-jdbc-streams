package com.ivanfranchin.storeapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

public record UpdateCustomerRequest(
        @Schema(example = "Ivan Franchin 2") String name,
        @Schema(example = "ivan.franchin.2@test.com") @Email String email,
        @Schema(example = "Street Bronx 456") String address,
        @Schema(example = "778899") String phone) {
}
