package com.mycompany.storeapi.service;

import com.mycompany.storeapi.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer saveCustomer(Customer customer);

    Customer validateAndGetCustomerById(Long id);

}
