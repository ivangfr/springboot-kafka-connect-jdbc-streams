package com.mycompany.storeapi.controller;

import com.mycompany.storeapi.dto.AddCustomerDto;
import com.mycompany.storeapi.dto.CustomerDto;
import com.mycompany.storeapi.dto.UpdateCustomerDto;
import com.mycompany.storeapi.model.Customer;
import com.mycompany.storeapi.service.CustomerService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final MapperFacade mapperFacade;

    public CustomerController(CustomerService customerService, MapperFacade mapperFacade) {
        this.customerService = customerService;
        this.mapperFacade = mapperFacade;
    }

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers()
                .stream()
                .map(customer -> mapperFacade.map(customer, CustomerDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CustomerDto getCustomer(@PathVariable Long id) {
        Customer customer = customerService.validateAndGetCustomerById(id);
        return mapperFacade.map(customer, CustomerDto.class);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerDto addCustomer(@Valid @RequestBody AddCustomerDto addCustomerDto) {
        Customer customer = mapperFacade.map(addCustomerDto, Customer.class);
        customer = customerService.saveCustomer(customer);
        return mapperFacade.map(customer, CustomerDto.class);
    }

    @PatchMapping("/{id}")
    public CustomerDto updateCustomer(@PathVariable Long id, @Valid @RequestBody UpdateCustomerDto updateCustomerDto) {
        Customer customer = customerService.validateAndGetCustomerById(id);
        mapperFacade.map(updateCustomerDto, customer);
        customer = customerService.saveCustomer(customer);
        return mapperFacade.map(customer, CustomerDto.class);
    }

}
