package com.mycompany.schoolstreams.bus.event;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class RegistrationDetailed {

    private String id;
    private String studentId;
    private String studentName;
    private String courseId;
    private String courseName;
    private LocalDateTime date;

}
