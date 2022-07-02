package it.theapplegeek.course.feign;

import feign.FeignException;
import it.theapplegeek.clients.enrolment.EnrolmentClient;
import it.theapplegeek.clients.student.StudentClient;
import it.theapplegeek.clients.student.StudentDto;
import it.theapplegeek.shared.exception.NotFoundException;
import it.theapplegeek.shared.exception.ServiceUnavailableException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log
@Component("EnrolmentFeignClient")
public class EnrolmentFeignClient {
    @Autowired private EnrolmentClient enrolmentClient;

    public void deleteEnrolmentByCourse(Long courseId) {
        try {
            log.info(
                    "##### Feign DELETE deleteEnrolmentByCourse -> delete enrolment of course with id "
                            + courseId
                            + " #####");
            enrolmentClient.deleteEnrolmentByCourse(courseId);
        } catch (FeignException e) {
            log.warning("##### FeignException -> " + e.getMessage() + " #####");
            throw new ServiceUnavailableException("Error when deleting enrolment of student with id " + courseId);
        }
    }
}
