package com.mycompany.storestreams.event;

import lombok.Data;

import java.util.Date;

@Data
public class OrderProduct {

    private String order_id;
    private Long product_id;
    private Integer unit;

}
