package com.mycompany.storeapi.rest;

import com.mycompany.storeapi.mapper.ProductMapper;
import com.mycompany.storeapi.model.Product;
import com.mycompany.storeapi.rest.dto.AddProductDto;
import com.mycompany.storeapi.rest.dto.ProductDto;
import com.mycompany.storeapi.rest.dto.UpdateProductDto;
import com.mycompany.storeapi.service.ProductService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts()
                .stream()
                .map(productMapper::toProductDto)
                .collect(Collectors.toList());
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Long id) {
        Product product = productService.validateAndGetProductById(id);
        return productMapper.toProductDto(product);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductDto addProduct(@Valid @RequestBody AddProductDto addProductDto) {
        Product product = productMapper.toProduct(addProductDto);
        product = productService.saveProduct(product);
        return productMapper.toProductDto(product);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PatchMapping("/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductDto updateProductDto) {
        Product product = productService.validateAndGetProductById(id);
        productMapper.updateProductFromDto(updateProductDto, product);
        product = productService.saveProduct(product);
        return productMapper.toProductDto(product);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    public ProductDto deleteProduct(@PathVariable Long id) {
        Product product = productService.validateAndGetProductById(id);
        productService.deleteProduct(product);
        return productMapper.toProductDto(product);
    }

}
