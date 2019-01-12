package com.mycompany.schoolapi.rest;

import com.mycompany.schoolapi.bus.SchoolStream;
import com.mycompany.schoolapi.bus.event.Course;
import com.mycompany.schoolapi.bus.event.Registration;
import com.mycompany.schoolapi.bus.event.Student;
import com.mycompany.schoolapi.rest.dto.CourseDto;
import com.mycompany.schoolapi.rest.dto.RegistrationDto;
import com.mycompany.schoolapi.rest.dto.StudentDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class SchoolController {

    private final SchoolStream schoolStream;
    private final MapperFacade mapperFacade;

    public SchoolController(SchoolStream schoolStream, MapperFacade mapperFacade) {
        this.schoolStream = schoolStream;
        this.mapperFacade = mapperFacade;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/students")
    public Student createStudents(@RequestBody StudentDto studentDto) {
        Student student = mapperFacade.map(studentDto, Student.class);
        student.setId(UUID.randomUUID().toString());
        schoolStream.processStudent(student);
        return student;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/courses")
    public Course createCourses(@RequestBody CourseDto courseDto) {
        Course course = mapperFacade.map(courseDto, Course.class);
        course.setId(UUID.randomUUID().toString());
        schoolStream.processCourse(course);
        return course;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registration")
    public Registration createRegistrations(@RequestBody RegistrationDto registrationDto) {
        Registration registration = mapperFacade.map(registrationDto, Registration.class);
        registration.setId(UUID.randomUUID().toString());
        schoolStream.processRegistration(registration);
        return registration;
    }
}
