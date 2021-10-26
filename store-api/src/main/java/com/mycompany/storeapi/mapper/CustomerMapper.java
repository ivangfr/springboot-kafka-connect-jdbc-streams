package com.mycompany.storeapi.mapper;

import com.mycompany.storeapi.model.Customer;
import com.mycompany.storeapi.rest.dto.AddCustomerRequest;
import com.mycompany.storeapi.rest.dto.CustomerResponse;
import com.mycompany.storeapi.rest.dto.UpdateCustomerRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerMapper {

    Customer toCustomer(AddCustomerRequest addCustomerRequest);

    CustomerResponse toCustomerResponse(Customer customer);

    void updateCustomerFromRequest(UpdateCustomerRequest updateCustomerRequest, @MappingTarget Customer customer);
}
