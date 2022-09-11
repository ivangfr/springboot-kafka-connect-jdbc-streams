package com.ivanfranchin.storeapi.rest.dto;

import java.math.BigDecimal;

public record ProductResponse(Long id, String name, BigDecimal price) {
}
