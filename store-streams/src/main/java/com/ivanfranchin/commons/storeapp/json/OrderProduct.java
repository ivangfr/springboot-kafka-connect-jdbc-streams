package com.ivanfranchin.commons.storeapp.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrderProduct(@JsonProperty("order_id") String orderId,
                           @JsonProperty("product_id") Long productId,
                           Integer unit) {
}
