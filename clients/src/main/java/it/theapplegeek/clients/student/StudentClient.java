package it.theapplegeek.clients.student;

import it.theapplegeek.clients.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "student", url="${student.ribbon.listOfServers}", configuration = FeignConfig.class)
public interface StudentClient {

    @GetMapping("/api/v1/student/{studentId}")
    StudentDto getStudent(@PathVariable("studentId") Long studentId);
    
    @DeleteMapping("/api/v1/student/cache/delete/{studentId}")
    void cleanStudentCacheById(@PathVariable("studentId") Long studentId);
}