package com.mycompany.schoolapi.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CourseDto {

    @ApiModelProperty(example = "Kafka Streams")
    @NotBlank
    private String name;

}
