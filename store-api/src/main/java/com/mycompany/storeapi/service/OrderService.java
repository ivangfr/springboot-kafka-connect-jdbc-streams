package com.mycompany.storeapi.service;

import com.mycompany.storeapi.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();

    Order saveOrder(Order order);

    Order validateAndGetOrderById(String id);

}
