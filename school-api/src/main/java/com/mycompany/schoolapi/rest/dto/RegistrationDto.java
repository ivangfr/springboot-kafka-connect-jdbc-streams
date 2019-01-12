package com.mycompany.schoolapi.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class RegistrationDto {

    @ApiModelProperty
    @NotNull
    private String studentId;

    @ApiModelProperty(position = 2)
    @NotNull
    private String courseId;

    @ApiModelProperty(position = 3)
    @NotNull
    private LocalDateTime date;

}
