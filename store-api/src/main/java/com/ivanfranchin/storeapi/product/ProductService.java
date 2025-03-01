package com.ivanfranchin.storeapi.product;

import com.ivanfranchin.storeapi.product.exception.ProductDeletionException;
import com.ivanfranchin.storeapi.product.exception.ProductNotFoundException;
import com.ivanfranchin.storeapi.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Product product) {
        try {
            productRepository.delete(product);
        } catch (DataIntegrityViolationException e) {
            throw new ProductDeletionException(product.getId());
        }
    }

    public Product validateAndGetProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }
}
