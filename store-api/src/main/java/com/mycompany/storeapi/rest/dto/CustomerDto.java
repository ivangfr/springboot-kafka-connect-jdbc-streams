package com.mycompany.storeapi.rest.dto;

import lombok.Data;

@Data
public class CustomerDto {

    private Long id;
    private String name;
    private String email;
    private String address;
    private String phone;

}
