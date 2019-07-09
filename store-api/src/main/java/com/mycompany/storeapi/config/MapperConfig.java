package com.mycompany.storeapi.config;

import com.mycompany.storeapi.rest.dto.CreateOrderDto;
import com.mycompany.storeapi.rest.dto.OrderDto;
import com.mycompany.storeapi.model.Customer;
import com.mycompany.storeapi.model.Order;
import com.mycompany.storeapi.model.OrderProduct;
import com.mycompany.storeapi.model.Product;
import com.mycompany.storeapi.service.CustomerService;
import com.mycompany.storeapi.service.ProductService;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    private final CustomerService customerService;
    private final ProductService productService;

    public MapperConfig(CustomerService customerService, ProductService productService) {
        this.customerService = customerService;
        this.productService = productService;
    }

    @Bean
    MapperFactory mapperFactory() {
        DefaultMapperFactory defaultMapperFactory = new DefaultMapperFactory.Builder()
                .useAutoMapping(true).mapNulls(false).build();

        // --
        // Order

        defaultMapperFactory.classMap(CreateOrderDto.class, Order.class)
                .byDefault()
                .customize(new CustomMapper<CreateOrderDto, Order>() {
                    @Override
                    public void mapAtoB(CreateOrderDto createOrderDto, Order order, MappingContext context) {
                        super.mapAtoB(createOrderDto, order, context);

                        Customer customer = customerService.validateAndGetCustomerById(createOrderDto.getCustomerId());
                        order.setCustomer(customer);

                        createOrderDto.getProducts().forEach(p -> {
                            Product product = productService.validateAndGetProductById(p.getId());
                            OrderProduct orderProduct = new OrderProduct();
                            orderProduct.setOrder(order);
                            orderProduct.setProduct(product);
                            orderProduct.setUnit(p.getUnit());
                            order.getOrderProducts().add(orderProduct);
                        });
                    }
                }).register();

        defaultMapperFactory.classMap(Order.class, OrderDto.class)
                .field("customer.id", "customerId")
                .field("orderProducts{product.id}", "products{id}")
                .field("orderProducts{unit}", "products{unit}")
                .byDefault()
                .register();

        return defaultMapperFactory;
    }

    @Bean
    MapperFacade mapperFacade() {
        return mapperFactory().getMapperFacade();
    }

}
