package com.ivanfranchin.storeapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class UpdateCustomerRequest {

    @Schema(example = "Ivan Franchin 2")
    private String name;

    @Schema(example = "ivan.franchin.2@test.com")
    @Email
    private String email;

    @Schema(example = "Street Bronx 456")
    private String address;

    @Schema(example = "778899")
    private String phone;
}
