package com.ivanfranchin.storeapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddCustomerRequest {

    @Schema(example = "Ivan Franchin")
    @NotBlank
    private String name;

    @Schema(example = "ivan.franchin@test.com")
    @NotBlank
    @Email
    private String email;

    @Schema(example = "Street Brooklyn 123")
    @NotBlank
    private String address;

    @Schema(example = "445566")
    @NotBlank
    private String phone;
}
