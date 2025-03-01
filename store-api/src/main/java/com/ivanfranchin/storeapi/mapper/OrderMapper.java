package com.ivanfranchin.storeapi.mapper;

import com.ivanfranchin.storeapi.model.OrderProduct;
import com.ivanfranchin.storeapi.model.Order;
import com.ivanfranchin.storeapi.rest.dto.CreateOrderRequest;
import com.ivanfranchin.storeapi.rest.dto.OrderResponse;
import com.ivanfranchin.storeapi.rest.dto.UpdateOrderRequest;
import com.ivanfranchin.storeapi.service.CustomerService;
import com.ivanfranchin.storeapi.service.ProductService;
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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "orderProducts", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract void updateOrderFromRequest(UpdateOrderRequest updateOrderRequest, @MappingTarget Order order);

    public Order toOrder(CreateOrderRequest createOrderRequest) {
        if (createOrderRequest == null) {
            return null;
        }
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
