package com.ivanfranchin.storeapi.product;

import com.ivanfranchin.storeapi.product.model.Product;
import com.ivanfranchin.storeapi.product.dto.AddProductRequest;
import com.ivanfranchin.storeapi.product.dto.ProductResponse;
import com.ivanfranchin.storeapi.product.dto.UpdateProductRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts()
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {
        Product product = productService.validateAndGetProductById(id);
        return ProductResponse.from(product);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductResponse addProduct(@Valid @RequestBody AddProductRequest addProductRequest) {
        Product product = Product.from(addProductRequest);
        product = productService.saveProduct(product);
        return ProductResponse.from(product);
    }

    @PatchMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductRequest updateProductRequest) {
        Product product = productService.validateAndGetProductById(id);
        Product.updateFrom(updateProductRequest, product);
        product = productService.saveProduct(product);
        return ProductResponse.from(product);
    }

    @DeleteMapping("/{id}")
    public ProductResponse deleteProduct(@PathVariable Long id) {
        Product product = productService.validateAndGetProductById(id);
        productService.deleteProduct(product);
        return ProductResponse.from(product);
    }
}
