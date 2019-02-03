package com.mycompany.storeapi.runner;

import com.mycompany.storeapi.model.Customer;
import com.mycompany.storeapi.model.Order;
import com.mycompany.storeapi.model.OrderProduct;
import com.mycompany.storeapi.model.OrderStatus;
import com.mycompany.storeapi.model.PaymentType;
import com.mycompany.storeapi.model.Product;
import com.mycompany.storeapi.service.CustomerService;
import com.mycompany.storeapi.service.OrderService;
import com.mycompany.storeapi.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Profile("simulation")
@Component
@org.springframework.core.annotation.Order(2)
public class SimulationRunner implements CommandLineRunner {

    @Value("${orders.total}")
    private Integer ordersTotal;

    @Value("${orders.delay-millis}")
    private Integer ordersDelayMillis;

    private final Random random = new Random();

    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderService orderService;

    public SimulationRunner(CustomerService customerService, ProductService productService, OrderService orderService) {
        this.customerService = customerService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) {
        log.info("Running order simulation ...");

        List<Customer> customers = customerService.getAllCustomers();
        List<Product> products = productService.getAllProducts();

        for (int i = 0; i < ordersTotal; i++) {
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

            orderService.saveOrder(order);

            log.info("Order created: {}", order);

            try {
                Thread.sleep(ordersDelayMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        log.info("Order simulation finished!");
    }

}
