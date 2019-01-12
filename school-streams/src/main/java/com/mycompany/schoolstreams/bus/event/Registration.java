package com.mycompany.schoolstreams.bus.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Registration {

    private String id;
    private String studentId;
    private String courseId;
    private LocalDateTime date;

}
