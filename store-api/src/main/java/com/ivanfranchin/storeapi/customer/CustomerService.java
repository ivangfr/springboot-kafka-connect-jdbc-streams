package com.ivanfranchin.storeapi.customer;

import com.ivanfranchin.storeapi.customer.exception.CustomerDeletionException;
import com.ivanfranchin.storeapi.customer.exception.CustomerNotFoundException;
import com.ivanfranchin.storeapi.customer.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Customer customer) {
        try {
            customerRepository.delete(customer);
        } catch (DataIntegrityViolationException e) {
            throw new CustomerDeletionException(customer.getId());
        }
    }

    public Customer validateAndGetCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }
}
