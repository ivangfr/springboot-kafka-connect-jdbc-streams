package com.mycompany.commons.storeapp.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Product {

    private Long id;
    private String name;
    private BigDecimal price;

    @JsonProperty("created_at")
    private Date createdAt;
}
