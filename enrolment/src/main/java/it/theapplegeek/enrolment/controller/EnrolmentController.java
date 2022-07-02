package it.theapplegeek.enrolment.controller;

import it.theapplegeek.clients.enrolment.CourseEnrolmentDto;
import it.theapplegeek.clients.enrolment.StudentEnrolmentDto;
import it.theapplegeek.enrolment.model.EnrolmentId;
import it.theapplegeek.enrolment.service.EnrolmentService;
import it.theapplegeek.shared.util.JsonUtils;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Log
@RestController
@RequestMapping("api/v1/enrolment")
public class EnrolmentController {

  @Autowired private EnrolmentService enrolmentService;

  @Autowired private JsonUtils jsonUtils;

  @GetMapping("student/{studentId}")
  public List<CourseEnrolmentDto> getCoursesByStudent(@PathVariable("studentId") Long studentId) {
    log.info("##### GET getCoursesByStudent -> get all course enrolments #####");
    return enrolmentService.getCoursesByStudent(studentId);
  }

  @GetMapping("course/{courseId}")
  public List<StudentEnrolmentDto> getStudentsByCourse(@PathVariable("courseId") Long courseId) {
    log.info("##### GET getStudentsByCourse -> get all student enrolments #####");
    return enrolmentService.getStudentsByCourse(courseId);
  }

  @PostMapping
  public void addEnrolment(
      @RequestParam(value = "createdDate", required = false) LocalDate createdDate,
      @RequestBody EnrolmentId enrolmentId) {
    log.info("##### POST addEnrolment -> add enrolment #####");
    log.info(
        "##### enrolmentId: \n" + jsonUtils.convertObjectToPrettyJson(enrolmentId) + "\n#####");
    enrolmentService.addEnrolment(enrolmentId, createdDate);
  }

  @DeleteMapping()
  public void deleteEnrolment(
      @RequestParam("studentId") Long studentId, @RequestParam("courseId") Long courseId) {
    log.info(
        "##### DELETE deleteEnrolment -> delete enrolment with student id "
            + studentId
            + " and course id "
            + courseId
            + " #####");
    enrolmentService.deleteEnrolment(studentId, courseId);
  }

  @DeleteMapping("student/{studentId}")
  public void deleteEnrolmentByStudent(@RequestParam("studentId") Long studentId) {
    log.info(
        "##### DELETE deleteEnrolmentByStudent -> delete student enrolments with id "
            + studentId
            + " #####");
    enrolmentService.deleteEnrolmentByStudent(studentId);
  }

  @DeleteMapping("course/{courseId}")
  public void deleteEnrolmentByCourse(@RequestParam("courseId") Long courseId) {
    log.info(
        "##### DELETE deleteEnrolmentByCourse -> delete course enrolments with id "
            + courseId
            + " #####");
    enrolmentService.deleteEnrolmentByCourse(courseId);
  }
}
