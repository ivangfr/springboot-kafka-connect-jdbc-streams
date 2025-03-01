package com.ivanfranchin.storeapi.rest.dto;

import com.ivanfranchin.storeapi.model.Customer;

public record CustomerResponse(Long id, String name, String email, String address, String phone) {

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getPhone()
        );
    }
}
