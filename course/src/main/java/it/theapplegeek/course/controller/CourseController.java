package it.theapplegeek.course.controller;

import it.theapplegeek.clients.course.CourseDto;
import it.theapplegeek.course.feign.EnrolmentFeignClient;
import it.theapplegeek.course.service.CourseService;
import it.theapplegeek.shared.util.JsonUtils;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Log
@RestController
@RequestMapping("api/v1/course")
public class CourseController {

  @Autowired private CourseService courseService;

  @Autowired private JsonUtils jsonUtils;

  @GetMapping
  public List<CourseDto> getCourses() {
    log.info("##### GET getCourses -> get all courses #####");
    return courseService.getCourses();
  }

  @GetMapping("{courseId}")
  public CourseDto getCourse(@PathVariable("courseId") Long courseId) {
    log.info("##### GET getCourse -> get course with id " + courseId + " #####");
    return courseService.getCourse(courseId);
  }

  @PostMapping
  public CourseDto addCourse(@Valid @RequestBody CourseDto courseDto) {
    log.info("##### POST addCourse -> add new course #####");
    log.info("##### courseDto: \n" + jsonUtils.convertObjectToPrettyJson(courseDto) + "\n#####");
    return courseService.addCourse(courseDto);
  }

  @PutMapping("{courseId}")
  public CourseDto updateCourse(
      @PathVariable("courseId") Long courseId, @RequestBody CourseDto courseDto) {
    log.info("##### PUT updateCourse -> update course with id " + courseId + " #####");
    log.info("##### courseDto: \n" + jsonUtils.convertObjectToPrettyJson(courseDto) + "\n#####");
    CourseDto courseUpdated = courseService.updateCourse(courseId, courseDto);
    log.info("##### Course with id " + courseUpdated.getId() + " updated #####");
    return courseUpdated;
  }

  @PutMapping("{courseId}/add_teacher")
  public void assignTeacherToCourse(
      @PathVariable("courseId") Long courseId, @RequestParam("teacherId") Long teacherId) {
    log.info(
        "##### PUT assignTeacherToCourse -> assign teacher with id "
            + teacherId
            + " to course with id "
            + courseId
            + " #####");
    courseService.assignTeacherToCourse(courseId, teacherId);
  }

  @DeleteMapping("detach_teacher/{teacherId}")
  public void detachTeacherFromAllCourses(@PathVariable("teacherId") Long teacherId) {
    log.info(
        "##### DELETE detachTeacherFromAllCourses -> detach teacher with id "
            + teacherId
            + " in all courses #####");
    courseService.detachTeacherFromAllCourses(teacherId);
  }

  @DeleteMapping("{courseId}")
  public void deleteCourse(@PathVariable("courseId") Long courseId) {
    log.info("##### DELETE deleteCourse -> delete course with id " + courseId + " #####");
    courseService.deleteCourse(courseId);
  }
}
