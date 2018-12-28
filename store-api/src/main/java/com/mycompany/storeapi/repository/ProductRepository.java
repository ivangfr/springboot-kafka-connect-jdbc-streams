package com.mycompany.storeapi.repository;

import com.mycompany.storeapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
