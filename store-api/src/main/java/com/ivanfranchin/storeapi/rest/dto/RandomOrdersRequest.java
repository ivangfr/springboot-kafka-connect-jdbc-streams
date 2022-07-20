package com.ivanfranchin.storeapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RandomOrdersRequest {

    @Schema(example = "10")
    private Integer total;

    @Schema(example = "100")
    private Integer sleep;
}
