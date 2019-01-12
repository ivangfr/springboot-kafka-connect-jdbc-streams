package com.mycompany.schoolapi.bus;

import com.mycompany.schoolapi.bus.event.Course;
import com.mycompany.schoolapi.bus.event.Registration;
import com.mycompany.schoolapi.bus.event.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableBinding(SchoolStreamsSource.class)
public class SchoolStream {

    private final SchoolStreamsSource schoolStreamsSource;

    public SchoolStream(SchoolStreamsSource schoolStreamsSource) {
        this.schoolStreamsSource = schoolStreamsSource;
    }

    public void processStudent(Student student) {
        Message<Student> message = MessageBuilder
                .withPayload(student)
                .setHeader(KafkaHeaders.MESSAGE_KEY, student.getId().getBytes())
                .build();
        schoolStreamsSource.studentOutput().send(message);
        log.info("Message sent successfully! {}", message);
    }

    public void processCourse(Course course) {
        Message<Course> message = MessageBuilder
                .withPayload(course)
                .setHeader(KafkaHeaders.MESSAGE_KEY, course.getId().getBytes())
                .build();
        schoolStreamsSource.courseOutput().send(message);
        log.info("Message sent successfully! {}", message);
    }

    public void processRegistration(Registration studentCourse) {
        Message<Registration> message = MessageBuilder
                .withPayload(studentCourse)
                .setHeader(KafkaHeaders.MESSAGE_KEY, studentCourse.getId().getBytes())
                .build();
        schoolStreamsSource.registrationOutput().send(message);
        log.info("Message sent successfully! {}", message);
    }

}
