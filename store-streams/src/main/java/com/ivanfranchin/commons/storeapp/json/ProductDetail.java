package com.ivanfranchin.commons.storeapp.json;

import lombok.Data;

import java.math.BigDecimal;

// If this class is converted to record, serialization exception will be thrown
@Data
public class ProductDetail {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer unit;
}
