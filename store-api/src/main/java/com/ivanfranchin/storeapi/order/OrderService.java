package com.ivanfranchin.storeapi.order;

import com.ivanfranchin.storeapi.customer.CustomerService;
import com.ivanfranchin.storeapi.order.dto.CreateOrderRequest;
import com.ivanfranchin.storeapi.order.exception.OrderNotFoundException;
import com.ivanfranchin.storeapi.order.model.Order;
import com.ivanfranchin.storeapi.order.model.OrderProduct;
import com.ivanfranchin.storeapi.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order validateAndGetOrderById(String id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Order createOrderFrom(CreateOrderRequest createOrderRequest) {
        Order order = new Order();
        order.setPaymentType(createOrderRequest.paymentType());
        order.setStatus(createOrderRequest.status());
        order.setCustomer(customerService.validateAndGetCustomerById(createOrderRequest.customerId()));

        for (CreateOrderRequest.CreateOrderProductRequest p : createOrderRequest.products()) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(productService.validateAndGetProductById(p.id()));
            orderProduct.setUnit(p.unit());
            order.getOrderProducts().add(orderProduct);
        }
        return order;
    }
}
