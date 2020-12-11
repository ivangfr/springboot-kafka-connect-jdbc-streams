package com.mycompany.commons.storeapp.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderProduct {

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("product_id")
    private Long productId;

    private Integer unit;

}
