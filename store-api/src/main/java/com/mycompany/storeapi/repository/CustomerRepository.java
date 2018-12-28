package com.mycompany.storeapi.repository;

import com.mycompany.storeapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
