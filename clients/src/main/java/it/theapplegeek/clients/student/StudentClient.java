package it.theapplegeek.clients.student;

import it.theapplegeek.clients.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "student", url="${student.ribbon.listOfServers}", configuration = FeignConfig.class)
public interface StudentClient {
    @DeleteMapping("/api/v1/student/cache/delete/{studentId}")
    void cleanStudentCacheById(@PathVariable("studentId") Long studentId);
}