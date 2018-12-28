package com.mycompany.storeapi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class UpdateCustomerDto {

    @ApiModelProperty(example = "Ivan Franchin Jr.")
    private String name;

    @ApiModelProperty(position = 2, example = "ivan.franchin.jr@test.com")
    @Email
    private String email;

    @ApiModelProperty(position = 3, example = "Street Bronx 456")
    private String address;

    @ApiModelProperty(position = 4, example = "778899")
    private String phone;

}
