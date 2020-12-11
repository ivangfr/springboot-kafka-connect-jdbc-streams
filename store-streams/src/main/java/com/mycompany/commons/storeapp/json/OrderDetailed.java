package com.mycompany.commons.storeapp.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDetailed {

    private String id;

    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("payment_type")
    private String paymentType;

    private String status;

    @JsonProperty("created_at")
    private Date createdAt;

    private List<ProductDetail> products;

}
