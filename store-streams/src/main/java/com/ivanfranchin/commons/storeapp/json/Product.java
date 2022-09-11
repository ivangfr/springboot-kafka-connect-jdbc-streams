package com.ivanfranchin.commons.storeapp.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

public record Product(Long id, String name, BigDecimal price,
                      @JsonProperty("created_at") Date createdAt) {
}
