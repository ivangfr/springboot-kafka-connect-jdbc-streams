package com.ivanfranchin.storeapi.rest;

import com.ivanfranchin.storeapi.model.Customer;
import com.ivanfranchin.storeapi.model.Order;
import com.ivanfranchin.storeapi.model.OrderProduct;
import com.ivanfranchin.storeapi.model.OrderStatus;
import com.ivanfranchin.storeapi.model.PaymentType;
import com.ivanfranchin.storeapi.model.Product;
import com.ivanfranchin.storeapi.rest.dto.RandomOrdersRequest;
import com.ivanfranchin.storeapi.service.CustomerService;
import com.ivanfranchin.storeapi.service.OrderService;
import com.ivanfranchin.storeapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/simulation")
public class SimulationController {

    @Value("${simulation.orders.total}")
    private Integer total;

    @Value("${simulation.orders.sleep}")
    private Integer sleep;

    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderService orderService;

    @PostMapping("/orders")
    public List<String> createRandomOrders(@RequestBody RandomOrdersRequest randomOrdersRequest) throws InterruptedException {
        total = randomOrdersRequest.total() == null ? total : randomOrdersRequest.total();
        sleep = randomOrdersRequest.sleep() == null ? sleep : randomOrdersRequest.sleep();

        log.info("## Running order simulation - total: {}, sleep: {}", total, sleep);

        List<String> orderIds = new ArrayList<>();
        List<Customer> customers = customerService.getAllCustomers();
        List<Product> products = productService.getAllProducts();

        for (int i = 0; i < total; i++) {
            Order order = new Order();
            order.setId(UUID.randomUUID().toString());
            order.setPaymentType(PaymentType.values()[random.nextInt(PaymentType.values().length)]);
            order.setStatus(OrderStatus.values()[random.nextInt(OrderStatus.values().length)]);

            Customer customer = customers.get(random.nextInt(customers.size()));
            order.setCustomer(customer);

            Set<OrderProduct> orderProducts = new HashSet<>();
            int numProducts = random.nextInt(3) + 1;
            for (int j = 0; j < numProducts; j++) {
                Product product = products.get(random.nextInt(products.size()));
                int unit = random.nextInt(3) + 1;

                Optional<OrderProduct> orderProductOptional = orderProducts.stream()
                        .filter(op -> op.getProduct().getId().equals(product.getId()))
                        .findAny();

                if (orderProductOptional.isPresent()) {
                    OrderProduct existingOrderProduct = orderProductOptional.get();
                    existingOrderProduct.setUnit(existingOrderProduct.getUnit() + unit);
                } else {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setProduct(product);
                    orderProduct.setUnit(unit);
                    orderProduct.setOrder(order);
                    orderProducts.add(orderProduct);
                }
            }
            order.setOrderProducts(orderProducts);

            order = orderService.saveOrder(order);
            orderIds.add(order.getId());
            log.info("Order created: {}", order);

            Thread.sleep(sleep);
        }

        log.info("## Order simulation finished successfully!");

        return orderIds;
    }

    private static final Random random = new SecureRandom();
}
