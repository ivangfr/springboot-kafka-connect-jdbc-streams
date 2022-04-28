package com.mycompany.storeapi.mapper;

import com.mycompany.storeapi.model.Customer;
import com.mycompany.storeapi.rest.dto.AddCustomerRequest;
import com.mycompany.storeapi.rest.dto.CustomerResponse;
import com.mycompany.storeapi.rest.dto.UpdateCustomerRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Customer toCustomer(AddCustomerRequest addCustomerRequest);

    CustomerResponse toCustomerResponse(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateCustomerFromRequest(UpdateCustomerRequest updateCustomerRequest, @MappingTarget Customer customer);
}
