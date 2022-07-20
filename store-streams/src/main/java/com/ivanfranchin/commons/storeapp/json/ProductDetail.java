package com.ivanfranchin.commons.storeapp.json;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDetail {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer unit;
}
