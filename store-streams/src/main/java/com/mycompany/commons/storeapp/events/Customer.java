package com.mycompany.commons.storeapp.events;

import lombok.Data;

import java.util.Date;

@Data
public class Customer {

    private String id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private Date created_at;

}
