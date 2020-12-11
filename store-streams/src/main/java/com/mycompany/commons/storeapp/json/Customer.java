package com.mycompany.commons.storeapp.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Customer {

    private Long id;
    private String name;
    private String email;
    private String address;
    private String phone;

    @JsonProperty("created_at")
    private Date createdAt;

}
