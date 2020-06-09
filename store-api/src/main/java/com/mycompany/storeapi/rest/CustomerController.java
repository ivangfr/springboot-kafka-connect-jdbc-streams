package com.mycompany.storeapi.rest;

import com.mycompany.storeapi.mapper.CustomerMapper;
import com.mycompany.storeapi.model.Customer;
import com.mycompany.storeapi.rest.dto.AddCustomerDto;
import com.mycompany.storeapi.rest.dto.CustomerDto;
import com.mycompany.storeapi.rest.dto.UpdateCustomerDto;
import com.mycompany.storeapi.service.CustomerService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers()
                .stream()
                .map(customerMapper::toCustomerDto)
                .collect(Collectors.toList());
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/{id}")
    public CustomerDto getCustomer(@PathVariable Long id) {
        Customer customer = customerService.validateAndGetCustomerById(id);
        return customerMapper.toCustomerDto(customer);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerDto addCustomer(@Valid @RequestBody AddCustomerDto addCustomerDto) {
        Customer customer = customerMapper.toCustomer(addCustomerDto);
        customer = customerService.saveCustomer(customer);
        return customerMapper.toCustomerDto(customer);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PatchMapping("/{id}")
    public CustomerDto updateCustomer(@PathVariable Long id, @Valid @RequestBody UpdateCustomerDto updateCustomerDto) {
        Customer customer = customerService.validateAndGetCustomerById(id);
        customerMapper.updateCustomerFromDto(updateCustomerDto, customer);
        customer = customerService.saveCustomer(customer);
        return customerMapper.toCustomerDto(customer);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    public CustomerDto deleteCustomer(@PathVariable Long id) {
        Customer customer = customerService.validateAndGetCustomerById(id);
        customerService.deleteCustomer(customer);
        return customerMapper.toCustomerDto(customer);
    }

}
