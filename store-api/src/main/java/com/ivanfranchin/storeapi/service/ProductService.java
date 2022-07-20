package com.ivanfranchin.storeapi.service;

import com.ivanfranchin.storeapi.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product saveProduct(Product product);

    void deleteProduct(Product product);

    Product validateAndGetProductById(Long id);
}
