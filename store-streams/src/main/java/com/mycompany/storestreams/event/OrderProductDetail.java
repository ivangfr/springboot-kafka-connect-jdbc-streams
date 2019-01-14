package com.mycompany.storestreams.event;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderProductDetail {

    private String order_id;
    private Long product_id;
    private String product_name;
    private BigDecimal product_price;
    private Integer unit;

}
