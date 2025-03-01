package com.ivanfranchin.storeapi.model;

import com.ivanfranchin.storeapi.rest.dto.AddCustomerRequest;
import com.ivanfranchin.storeapi.rest.dto.UpdateCustomerRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onPrePersist() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onPreUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public static Customer from(AddCustomerRequest addCustomerRequest) {
        Customer customer = new Customer();
        customer.setName(addCustomerRequest.name());
        customer.setEmail(addCustomerRequest.email());
        customer.setAddress(addCustomerRequest.address());
        customer.setPhone(addCustomerRequest.phone());
        return customer;
    }

    public static void updateFrom(UpdateCustomerRequest updateCustomerRequest, Customer customer) {
        if (updateCustomerRequest.name() != null) {
            customer.setName(updateCustomerRequest.name());
        }
        if (updateCustomerRequest.email() != null) {
            customer.setEmail(updateCustomerRequest.email());
        }
        if (updateCustomerRequest.address() != null) {
            customer.setAddress(updateCustomerRequest.address());
        }
        if (updateCustomerRequest.phone() != null) {
            customer.setPhone(updateCustomerRequest.phone());
        }
    }
}
