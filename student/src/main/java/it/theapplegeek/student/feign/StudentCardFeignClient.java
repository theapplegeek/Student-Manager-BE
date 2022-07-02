package it.theapplegeek.student.feign;

import feign.FeignException;
import it.theapplegeek.clients.studentcard.StudentCardClient;
import it.theapplegeek.clients.studentcard.StudentCardDto;
import it.theapplegeek.shared.exception.NotFoundException;
import it.theapplegeek.shared.exception.ServiceUnavailableException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log
@Component("StudentCardFeignClient")
public class StudentCardFeignClient {
    @Autowired
    private StudentCardClient studentCardClient;

    public StudentCardDto getStudentCardByStudentId(Long studentId) {
        try {
            log.info("##### Feign GET getStudentCardByStudentId -> get student card with student id " + studentId + " #####");
            return studentCardClient.getStudentCardByStudentId(studentId);
        } catch (FeignException e) {
            log.warning("##### FeignException -> " + e.getMessage() + " #####");
            return null;
        } catch (NotFoundException e) {
            log.warning("##### NotFoundException -> " + e.getMessage() + " #####");
            return null;
        }
    }

    public void deleteStudentCard(Long studentId) {
        try {
            log.info("##### Feign DELETE deleteStudentCardByStudentId -> delete cache of student with id " + studentId + " #####");
            studentCardClient.deleteStudentCardByStudentId(studentId);
        } catch (FeignException e) {
            log.warning("##### FeignException -> " + e.getMessage() + " #####");
            log.warning("##### Error when deleting a student card of student with id " + studentId + " #####");
            throw new ServiceUnavailableException("Error when deleting a student card of student with id " + studentId);
        } catch (NotFoundException e) {
            log.warning("##### NotFoundException ->" + e.getMessage() + " #####");
        }
    }
}
