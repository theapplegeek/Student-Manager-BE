package it.theapplegeek.enrolment.feign;

import feign.FeignException;
import it.theapplegeek.clients.student.StudentClient;
import it.theapplegeek.clients.student.StudentDto;
import it.theapplegeek.shared.exception.NotFoundException;
import it.theapplegeek.shared.exception.ServiceUnavailableException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log
@Component("StudentFeignClient")
public class StudentFeignClient {
    @Autowired
    private StudentClient studentClient;

    public StudentDto getStudent(Long studentId) {
        try {
            log.info("##### Feign GET getStudent -> get student with id " + studentId + " #####");
            return studentClient.getStudent(studentId);
        } catch (FeignException e) {
            log.warning("##### FeignException -> " + e.getMessage() + " #####");
            log.warning("##### Error when get student with id " + studentId + " #####");
            throw new ServiceUnavailableException("Error when get student with id " + studentId);
        } catch (NotFoundException e) {
            log.warning("##### NotFoundException -> " + e.getMessage() + " #####");
            log.warning("##### Error when get student with id " + studentId + " #####");
            return null;
        }
    }
}
