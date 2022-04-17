package it.theapplegeek.clients.student;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "student",
        url = "${clients.student.url}"
)
public interface StudentClient {

    @GetMapping("{studentId}")
    StudentDto getStudent(@PathVariable("studentId") Long studentId);
}
