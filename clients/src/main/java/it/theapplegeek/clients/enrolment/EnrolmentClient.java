package it.theapplegeek.clients.enrolment;

import it.theapplegeek.clients.config.FeignConfig;
import it.theapplegeek.clients.student.StudentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "enrolment", url="${enrolment.ribbon.listOfServers}", configuration = FeignConfig.class)
public interface EnrolmentClient {

    @DeleteMapping("/api/v1/enrolment/student/{studentId}")
    void deleteEnrolmentByStudent(@PathVariable("studentId") Long studentId);

    @DeleteMapping("/api/v1/enrolment/course/{courseId}")
    void deleteEnrolmentByCourse(@PathVariable("courseId") Long courseId);
}