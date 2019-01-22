package com.mycompany.commons.storeapp.events;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDetail {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer unit;

}
