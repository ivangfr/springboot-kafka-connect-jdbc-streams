package com.ivanfranchin.commons.storeapp.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record Order(String id,
                    @JsonProperty("customer_id") Long customerId,
                    @JsonProperty("payment_type") String paymentType,
                    String status,
                    @JsonProperty("created_at") Date createdAt) {
}
