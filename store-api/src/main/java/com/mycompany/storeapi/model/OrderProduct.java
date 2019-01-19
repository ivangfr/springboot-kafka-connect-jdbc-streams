package com.mycompany.storeapi.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders_products")
@IdClass(OrderProductPk.class)
public class OrderProduct {

    @Id
    @ManyToOne
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "FK_ORDER"))
    private Order order;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "FK_PRODUCT"))
    private Product product;

    @Column(nullable = false)
    private Integer unit;

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

}
