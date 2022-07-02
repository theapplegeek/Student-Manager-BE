package it.theapplegeek.enrolment.feign;

import feign.FeignException;
import it.theapplegeek.clients.course.CourseClient;
import it.theapplegeek.clients.course.CourseDto;
import it.theapplegeek.clients.student.StudentDto;
import it.theapplegeek.shared.exception.NotFoundException;
import it.theapplegeek.shared.exception.ServiceUnavailableException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log
@Component("CourseFeignClient")
public class CourseFeignClient {
    @Autowired
    private CourseClient courseClient;

    public CourseDto getCourse(Long courseId) {
        try {
            log.info("##### Feign GET getCourse -> get course with id " + courseId + " #####");
            return courseClient.getCourse(courseId);
        } catch (FeignException e) {
            log.warning("##### FeignException -> " + e.getMessage() + " #####");
            log.warning("##### Error when get course with id " + courseId + " #####");
            throw new ServiceUnavailableException("Error when get course with id " + courseId);
        } catch (NotFoundException e) {
            log.warning("##### NotFoundException -> " + e.getMessage() + " #####");
            log.warning("##### Error when get course with id " + courseId + " #####");
            return null;
        }
    }
}
