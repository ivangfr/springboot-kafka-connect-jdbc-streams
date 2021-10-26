package com.mycompany.storeapi.mapper;

import com.mycompany.storeapi.model.Order;
import com.mycompany.storeapi.model.OrderProduct;
import com.mycompany.storeapi.rest.dto.CreateOrderRequest;
import com.mycompany.storeapi.rest.dto.OrderResponse;
import com.mycompany.storeapi.rest.dto.UpdateOrderRequest;
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
    public abstract OrderResponse toOrderResponse(Order order);

    @Mapping(source = "product.id", target = "id")
    public abstract OrderResponse.ProductResponse toOrderResponseProductResponse(OrderProduct orderProduct);

    public abstract void updateOrderFromRequest(UpdateOrderRequest updateOrderRequest, @MappingTarget Order order);

    public Order toOrder(CreateOrderRequest createOrderRequest) {
        if (createOrderRequest == null) {
            return null;
        }
        Order order = new Order();
        order.setPaymentType(createOrderRequest.getPaymentType());
        order.setStatus(createOrderRequest.getStatus());
        order.setCustomer(customerService.validateAndGetCustomerById(createOrderRequest.getCustomerId()));

        for (CreateOrderRequest.CreateOrderProductRequest p : createOrderRequest.getProducts()) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(productService.validateAndGetProductById(p.getId()));
            orderProduct.setUnit(p.getUnit());
            order.getOrderProducts().add(orderProduct);
        }
        return order;
    }
}
