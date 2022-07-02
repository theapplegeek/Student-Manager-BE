package it.theapplegeek.clients.course;

import it.theapplegeek.clients.config.FeignConfig;
import it.theapplegeek.clients.student.StudentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "course", url="${course.ribbon.listOfServers}", configuration = FeignConfig.class)
public interface CourseClient {

    @GetMapping("/api/v1/course/{courseId}")
    CourseDto getCourse(@PathVariable("courseId") Long courseId);

    @DeleteMapping("detach_teacher/{teacherId}")
    void detachTeacherFromAllCourses(@PathVariable("teacherId") Long teacherId);
}