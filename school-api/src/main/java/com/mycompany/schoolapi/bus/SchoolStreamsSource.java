package com.mycompany.schoolapi.bus;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface SchoolStreamsSource {

    String STUDENT_OUTPUT = "student-output";
    String COURSE_OUTPUT = "course-output";
    String REGISTRATION_OUTPUT = "registration-output";

    @Output(STUDENT_OUTPUT)
    MessageChannel studentOutput();

    @Output(COURSE_OUTPUT)
    MessageChannel courseOutput();

    @Output(REGISTRATION_OUTPUT)
    MessageChannel registrationOutput();

}
