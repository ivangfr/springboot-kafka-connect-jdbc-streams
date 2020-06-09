package com.mycompany.storeapi.mapper;

import com.mycompany.storeapi.model.Product;
import com.mycompany.storeapi.rest.dto.AddProductDto;
import com.mycompany.storeapi.rest.dto.ProductDto;
import com.mycompany.storeapi.rest.dto.UpdateProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProductMapper {

    Product toProduct(AddProductDto addProductDto);

    ProductDto toProductDto(Product product);

    void updateProductFromDto(UpdateProductDto updateProductDto, @MappingTarget Product product);

}
