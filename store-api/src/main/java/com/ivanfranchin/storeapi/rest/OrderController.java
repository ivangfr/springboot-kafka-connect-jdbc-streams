package com.ivanfranchin.storeapi.rest;

import com.ivanfranchin.storeapi.mapper.OrderMapper;
import com.ivanfranchin.storeapi.model.Order;
import com.ivanfranchin.storeapi.rest.dto.OrderResponse;
import com.ivanfranchin.storeapi.service.OrderService;
import com.ivanfranchin.storeapi.rest.dto.CreateOrderRequest;
import com.ivanfranchin.storeapi.rest.dto.UpdateOrderRequest;
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

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders()
                .stream()
                .map(orderMapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable UUID id) {
        Order order = orderService.validateAndGetOrderById(id.toString());
        return orderMapper.toOrderResponse(order);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest) {
        Order order = orderMapper.toOrder(createOrderRequest);
        order.setId(UUID.randomUUID().toString());
        order = orderService.saveOrder(order);
        return orderMapper.toOrderResponse(order);
    }

    @PatchMapping("/{id}")
    public OrderResponse updateOrder(@PathVariable UUID id, @Valid @RequestBody UpdateOrderRequest updateOrderRequest) {
        Order order = orderService.validateAndGetOrderById(id.toString());
        orderMapper.updateOrderFromRequest(updateOrderRequest, order);
        order = orderService.saveOrder(order);
        return orderMapper.toOrderResponse(order);
    }
}
