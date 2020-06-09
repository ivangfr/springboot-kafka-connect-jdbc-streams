package com.mycompany.storeapi.mapper;

import com.mycompany.storeapi.model.Customer;
import com.mycompany.storeapi.rest.dto.AddCustomerDto;
import com.mycompany.storeapi.rest.dto.CustomerDto;
import com.mycompany.storeapi.rest.dto.UpdateCustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerMapper {

    Customer toCustomer(AddCustomerDto addCustomerDto);

    CustomerDto toCustomerDto(Customer customer);

    void updateCustomerFromDto(UpdateCustomerDto updateCustomerDto, @MappingTarget Customer customer);

}
