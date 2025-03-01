package com.ivanfranchin.storeapi.order;

import com.ivanfranchin.storeapi.order.model.Order;
import com.ivanfranchin.storeapi.order.model.OrderProduct;
import com.ivanfranchin.storeapi.order.dto.CreateOrderRequest;
import com.ivanfranchin.storeapi.order.dto.OrderResponse;
import com.ivanfranchin.storeapi.order.dto.UpdateOrderRequest;
import com.ivanfranchin.storeapi.customer.CustomerService;
import com.ivanfranchin.storeapi.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;

    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders()
                .stream()
                .map(OrderResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable UUID id) {
        Order order = orderService.validateAndGetOrderById(id.toString());
        return OrderResponse.from(order);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest) {
        Order order = toOrder(createOrderRequest);
        order.setId(UUID.randomUUID().toString());
        order = orderService.saveOrder(order);
        return OrderResponse.from(order);
    }

    @PatchMapping("/{id}")
    public OrderResponse updateOrder(@PathVariable UUID id, @Valid @RequestBody UpdateOrderRequest updateOrderRequest) {
        Order order = orderService.validateAndGetOrderById(id.toString());
        Order.updateFrom(updateOrderRequest, order);
        order = orderService.saveOrder(order);
        return OrderResponse.from(order);
    }

    public Order toOrder(CreateOrderRequest createOrderRequest) {
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
