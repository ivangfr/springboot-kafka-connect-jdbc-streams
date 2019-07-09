package com.mycompany.storeapi.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RandomOrdersDto {

    @ApiModelProperty(example = "10")
    private Integer total;

    @ApiModelProperty(position = 2, example = "100")
    private Integer sleep;

}
