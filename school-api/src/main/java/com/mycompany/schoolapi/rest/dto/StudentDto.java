package com.mycompany.schoolapi.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StudentDto {

    @ApiModelProperty(example = "Ivan Franchin")
    @NotBlank
    private String name;

}
