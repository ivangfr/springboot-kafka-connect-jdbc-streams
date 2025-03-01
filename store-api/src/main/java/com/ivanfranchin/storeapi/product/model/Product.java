package com.ivanfranchin.storeapi.product.model;

import com.ivanfranchin.storeapi.product.dto.AddProductRequest;
import com.ivanfranchin.storeapi.product.dto.UpdateProductRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    // Using String as type for price field for now due to the issue below
    // numeric.mapping doesn't work for DECIMAL fields #563: https://github.com/confluentinc/kafka-connect-jdbc/issues/563
    // private BigDecimal price;
    private String price;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onPrePersist() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onPreUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public static Product from(AddProductRequest addProductRequest) {
        Product product = new Product();
        product.setName(addProductRequest.name());
        product.setPrice(addProductRequest.price().toString());
        return product;
    }

    public static void updateFrom(UpdateProductRequest updateProductRequest, Product product) {
        if (updateProductRequest.name() != null) {
            product.setName(updateProductRequest.name());
        }
        if (updateProductRequest.price() != null) {
            product.setPrice(updateProductRequest.price().toString());
        }
    }
}
