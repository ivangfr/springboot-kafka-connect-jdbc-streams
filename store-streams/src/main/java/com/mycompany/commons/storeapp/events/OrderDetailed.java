package com.mycompany.commons.storeapp.events;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class OrderDetailed {

    private String id;
    private Long customer_id;
    private String customer_name;
    private String payment_type;
    private String status;
    private Date created_at;
    private Set products;

}
