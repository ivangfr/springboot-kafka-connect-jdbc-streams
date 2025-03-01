package com.ivanfranchin.storeapi.product.dto;

import com.ivanfranchin.storeapi.product.model.Product;

import java.math.BigDecimal;

public record ProductResponse(Long id, String name, BigDecimal price) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                new BigDecimal(product.getPrice())
        );
    }
}
