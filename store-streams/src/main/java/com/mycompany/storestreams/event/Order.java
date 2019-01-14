package com.mycompany.storestreams.event;

import lombok.Data;

import java.util.Date;

@Data
public class Order {

    private String id;
    private Long customer_id;
    private String payment_type;
    private String status;
    private Date created_at;

}
