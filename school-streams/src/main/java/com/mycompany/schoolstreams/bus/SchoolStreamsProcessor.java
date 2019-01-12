package com.mycompany.schoolstreams.bus;

import com.mycompany.schoolstreams.bus.event.Course;
import com.mycompany.schoolstreams.bus.event.Registration;
import com.mycompany.schoolstreams.bus.event.Student;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface SchoolStreamsProcessor {

    String STUDENT_INPUT = "student-input";
    String COURSE_INPUT = "course-input";
    String REGISTRATION_INPUT = "registration-input";
//    String REGISTRATION_OUTPUT = "registration-output";

    @Input(STUDENT_INPUT)
    KStream<Long, Student> studentInput();

    @Input(COURSE_INPUT)
    KStream<Long, Course> courseInput();

    @Input(REGISTRATION_INPUT)
    KStream<String, Registration> registrationInput();

//    @Output(REGISTRATION_OUTPUT)
//    KStream<String, RegistrationDetailed> registrationOutput();

}
