package it.theapplegeek.clients.studentcard;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("student-card")
public interface StudentCardClient {

    @GetMapping("/api/v1/student_card/student/{studentId}")
    StudentCardDto getStudentCardByStudentId(@PathVariable("studentId") Long studentId);
}