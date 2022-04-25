package it.theapplegeek.clients.student;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("student")
public interface StudentClient {
    @GetMapping("/api/v1/student/{studentId}")
    StudentDto getStudent(@PathVariable("studentId") Long studentId);
}