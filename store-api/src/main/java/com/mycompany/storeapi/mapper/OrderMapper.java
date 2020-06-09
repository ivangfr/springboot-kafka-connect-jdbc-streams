package com.mycompany.storeapi.mapper;

import com.mycompany.storeapi.model.Customer;
import com.mycompany.storeapi.model.Order;
import com.mycompany.storeapi.model.OrderProduct;
import com.mycompany.storeapi.model.Product;
import com.mycompany.storeapi.rest.dto.CreateOrderDto;
import com.mycompany.storeapi.rest.dto.OrderDto;
import com.mycompany.storeapi.rest.dto.UpdateOrderDto;
import com.mycompany.storeapi.service.CustomerService;
import com.mycompany.storeapi.service.ProductService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CustomerService.class, ProductService.class}
)
public abstract class OrderMapper {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "orderProducts", target = "products")
    public abstract OrderDto toOrderDto(Order order);

    // It's used in the mapping from Order to OrderDto
    @Mapping(source = "product.id", target = "id")
    public abstract OrderDto.ProductDto toOrderDtoProductDto(OrderProduct orderProduct);

    public abstract void updateOrderFromDto(UpdateOrderDto updateOrderDto, @MappingTarget Order order);

    public Order toOrder(CreateOrderDto createOrderDto) {
        Order order = new Order();
        order.setPaymentType(createOrderDto.getPaymentType());
        order.setStatus(createOrderDto.getStatus());

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

        return order;
    }

}
