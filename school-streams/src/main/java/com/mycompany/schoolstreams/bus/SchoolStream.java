package com.mycompany.schoolstreams.bus;

import com.mycompany.schoolstreams.bus.event.Course;
import com.mycompany.schoolstreams.bus.event.Registration;
import com.mycompany.schoolstreams.bus.event.Student;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableBinding(SchoolStreamsProcessor.class)
public class SchoolStream {

    @StreamListener
//    @SendTo("output")
    public /*KStream<?, ?>*/ void process(
            @Input(SchoolStreamsProcessor.STUDENT_INPUT) KStream<String, Student> studentKStream,
            @Input(SchoolStreamsProcessor.COURSE_INPUT) KStream<String, Course> courseKStream,
            @Input(SchoolStreamsProcessor.REGISTRATION_INPUT) KStream<String, Registration> registrationKStream) {

        studentKStream.foreach((key, value) -> log.info("key: {}, value: {}", key, value));
        courseKStream.foreach((key, value) -> log.info("key: {}, value: {}", key, value));
        registrationKStream.foreach((key, value) -> log.info("key: {}, value: {}", key, value));
    }

}
