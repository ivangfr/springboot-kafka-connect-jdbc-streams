package com.mycompany.storeapi.mapper;

import com.mycompany.storeapi.model.Product;
import com.mycompany.storeapi.rest.dto.AddProductRequest;
import com.mycompany.storeapi.rest.dto.ProductResponse;
import com.mycompany.storeapi.rest.dto.UpdateProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProductMapper {

    Product toProduct(AddProductRequest addProductRequest);

    ProductResponse toProductResponse(Product product);

    void updateProductFromRequest(UpdateProductRequest updateProductRequest, @MappingTarget Product product);
}
