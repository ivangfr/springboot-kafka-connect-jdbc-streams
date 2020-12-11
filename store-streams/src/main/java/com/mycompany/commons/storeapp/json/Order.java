package com.mycompany.commons.storeapp.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Order {

    private String id;

    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("payment_type")
    private String paymentType;

    private String status;

    @JsonProperty("created_at")
    private Date createdAt;

}
