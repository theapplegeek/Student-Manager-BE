package it.theapplegeek.clients.student;

import it.theapplegeek.clients.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "student", configuration = FeignConfig.class)
public interface StudentClient {
    @GetMapping("/api/v1/student/{studentId}")
    StudentDto getStudent(@PathVariable("studentId") Long studentId);
}