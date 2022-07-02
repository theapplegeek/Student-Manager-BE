package it.theapplegeek.student.feign;

import feign.FeignException;
import it.theapplegeek.clients.enrolment.EnrolmentClient;
import it.theapplegeek.clients.studentcard.StudentCardDto;
import it.theapplegeek.shared.exception.NotFoundException;
import it.theapplegeek.shared.exception.ServiceUnavailableException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log
@Component("EnrolmentFeignClient")
public class EnrolmentFeignClient {
  @Autowired private EnrolmentClient enrolmentClient;

  public void deleteEnrolmentByStudent(Long studentId) {
    try {
      log.info(
          "##### Feign DELETE deleteEnrolmentByStudent -> delete enrolment of student with id "
              + studentId
              + " #####");
      enrolmentClient.deleteEnrolmentByStudent(studentId);
    } catch (FeignException e) {
      log.warning("##### FeignException -> " + e.getMessage() + " #####");
      throw new ServiceUnavailableException("Error when deleting enrolment of student with id " + studentId);
    }
  }
}
