package com.mycompany.storestreams.config;

import com.mycompany.storestreams.event.OrderProductDetail;
import com.mycompany.storestreams.event.ProductDetail;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    MapperFactory mapperFactory() {
        DefaultMapperFactory defaultMapperFactory = new DefaultMapperFactory.Builder().useAutoMapping(true).build();

        defaultMapperFactory.classMap(OrderProductDetail.class, ProductDetail.class)
                .field("product_id", "id")
                .field("product_name", "name")
                .field("product_price", "price")
                .byDefault()
                .register();

        return defaultMapperFactory;
    }

    @Bean
    MapperFacade mapperFacade() {
        return mapperFactory().getMapperFacade();
    }

}
