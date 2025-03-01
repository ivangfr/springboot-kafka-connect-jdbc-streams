package com.ivanfranchin.storeapi.customer;

import com.ivanfranchin.storeapi.customer.model.Customer;
import com.ivanfranchin.storeapi.customer.dto.AddCustomerRequest;
import com.ivanfranchin.storeapi.customer.dto.CustomerResponse;
import com.ivanfranchin.storeapi.customer.dto.UpdateCustomerRequest;
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
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers()
                .stream()
                .map(CustomerResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomer(@PathVariable Long id) {
        Customer customer = customerService.validateAndGetCustomerById(id);
        return CustomerResponse.from(customer);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerResponse addCustomer(@Valid @RequestBody AddCustomerRequest addCustomerRequest) {
        Customer customer = Customer.from(addCustomerRequest);
        customer = customerService.saveCustomer(customer);
        return CustomerResponse.from(customer);
    }

    @PatchMapping("/{id}")
    public CustomerResponse updateCustomer(@PathVariable Long id, @Valid @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = customerService.validateAndGetCustomerById(id);
        Customer.updateFrom(updateCustomerRequest, customer);
        customer = customerService.saveCustomer(customer);
        return CustomerResponse.from(customer);
    }

    @DeleteMapping("/{id}")
    public CustomerResponse deleteCustomer(@PathVariable Long id) {
        Customer customer = customerService.validateAndGetCustomerById(id);
        customerService.deleteCustomer(customer);
        return CustomerResponse.from(customer);
    }
}
