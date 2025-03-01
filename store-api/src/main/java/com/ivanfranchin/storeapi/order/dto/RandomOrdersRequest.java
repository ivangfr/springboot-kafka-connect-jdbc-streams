package com.ivanfranchin.storeapi.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record RandomOrdersRequest(
        @Schema(example = "10") Integer total,
        @Schema(example = "100") Integer sleep) {
}
