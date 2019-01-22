package com.mycompany.commons.storeapp.events;

import lombok.Data;

@Data
public class OrderProduct {

    private String order_id;
    private Long product_id;
    private Integer unit;

}
