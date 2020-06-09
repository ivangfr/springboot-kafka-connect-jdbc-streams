package com.mycompany.storestreams.mapper;

import com.mycompany.commons.storeapp.events.Order;
import com.mycompany.commons.storeapp.events.OrderDetailed;
import com.mycompany.commons.storeapp.events.OrderProduct;
import com.mycompany.commons.storeapp.events.OrderProductDetail;
import com.mycompany.commons.storeapp.events.ProductDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface OrderProductMapper {

    OrderDetailed toOrderDetailed(Order order);

    OrderProductDetail toOrderProductDetail(OrderProduct orderProduct);

    @Mapping(source = "product_id", target = "id")
    @Mapping(source = "product_name", target = "name")
    @Mapping(source = "product_price", target = "price")
    ProductDetail toProductDetail(OrderProductDetail orderProductDetail);

}
