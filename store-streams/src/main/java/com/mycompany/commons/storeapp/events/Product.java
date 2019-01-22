package com.mycompany.commons.storeapp.events;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {

    private String id;
    private String name;
    private BigDecimal price;

}
