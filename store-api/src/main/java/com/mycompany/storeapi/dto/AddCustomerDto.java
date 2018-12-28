package com.mycompany.storeapi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class AddCustomerDto {

    @ApiModelProperty(example = "Ivan Franchin")
    @NotBlank
    private String name;

    @ApiModelProperty(position = 2, example = "ivan.franchin@test.com")
    @NotBlank
    @Email
    private String email;

    @ApiModelProperty(position = 3, example = "Street Brooklyn 123")
    @NotBlank
    private String address;

    @ApiModelProperty(position = 4, example = "445566")
    @NotBlank
    private String phone;

}
