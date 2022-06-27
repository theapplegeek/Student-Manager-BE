package it.theapplegeek.clients.studentcard;

import it.theapplegeek.clients.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "student-card", url="${student-card.ribbon.listOfServers}", configuration = FeignConfig.class)
public interface StudentCardClient {
    @GetMapping("/api/v1/student_card/student/{studentId}")
    StudentCardDto getStudentCardByStudentId(@PathVariable("studentId") Long studentId);

    @DeleteMapping("/api/v1/student_card/student/{studentId}")
    void deleteStudentCardByStudentId(@PathVariable("studentId") Long studentId);
}