package com.mycompany.storeapi.rest;

import com.mycompany.storeapi.mapper.OrderMapper;
import com.mycompany.storeapi.model.Order;
import com.mycompany.storeapi.rest.dto.CreateOrderDto;
import com.mycompany.storeapi.rest.dto.OrderDto;
import com.mycompany.storeapi.rest.dto.UpdateOrderDto;
import com.mycompany.storeapi.service.OrderService;
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
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders()
                .stream()
                .map(orderMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable UUID id) {
        Order order = orderService.validateAndGetOrderById(id.toString());
        return orderMapper.toOrderDto(order);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderDto createOrder(@Valid @RequestBody CreateOrderDto createOrderDto) {
        Order order = orderMapper.toOrder(createOrderDto);
        order.setId(UUID.randomUUID().toString());
        order = orderService.saveOrder(order);
        return orderMapper.toOrderDto(order);
    }

    @PatchMapping("/{id}")
    public OrderDto updateOrder(@PathVariable UUID id, @Valid @RequestBody UpdateOrderDto updateOrderDto) {
        Order order = orderService.validateAndGetOrderById(id.toString());
        orderMapper.updateOrderFromDto(updateOrderDto, order);
        order = orderService.saveOrder(order);
        return orderMapper.toOrderDto(order);
    }

}
