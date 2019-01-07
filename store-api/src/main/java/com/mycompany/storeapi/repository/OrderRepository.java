package com.mycompany.storeapi.repository;

import com.mycompany.storeapi.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
