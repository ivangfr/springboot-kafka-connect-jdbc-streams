package com.mycompany.storeapi.service;

import com.mycompany.storeapi.exception.ProductDeletionException;
import com.mycompany.storeapi.exception.ProductNotFoundException;
import com.mycompany.storeapi.model.Product;
import com.mycompany.storeapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Product product) {
        try {
            productRepository.delete(product);
        } catch (DataIntegrityViolationException e) {
            throw new ProductDeletionException(product.getId());
        }
    }

    @Override
    public Product validateAndGetProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }
}
