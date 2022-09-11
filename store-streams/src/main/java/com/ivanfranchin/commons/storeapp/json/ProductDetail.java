package com.ivanfranchin.commons.storeapp.json;

import java.math.BigDecimal;

public record ProductDetail(Long id, String name, BigDecimal price, Integer unit) {
}
