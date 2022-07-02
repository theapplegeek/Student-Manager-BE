package it.theapplegeek.teacher.feign;

import feign.FeignException;
import it.theapplegeek.clients.course.CourseClient;
import it.theapplegeek.shared.exception.ServiceUnavailableException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log
@Component("CourseFeignClient")
public class CourseFeignClient {
  @Autowired private CourseClient courseClient;

  public void detachTeacherFromAllCourses(Long teacherId) {
    try {
      log.info(
          "##### Feign DELETE detachTeacherFromAllCourses -> detach teacher with id "
              + teacherId
              + " in all courses #####");
      courseClient.detachTeacherFromAllCourses(teacherId);
    } catch (FeignException e) {
      log.warning("##### FeignException -> " + e.getMessage() + " #####");
      throw new ServiceUnavailableException(
          "Error when detaching teacher with id " + teacherId + " in all courses");
    }
  }
}
