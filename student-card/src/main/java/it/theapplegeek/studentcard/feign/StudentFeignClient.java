package it.theapplegeek.studentcard.feign;

import feign.FeignException;
import it.theapplegeek.clients.student.StudentClient;
import it.theapplegeek.shared.exception.ServiceUnavailableException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log
@Component("StudentFeignClient")
public class StudentFeignClient {
    @Autowired
    private StudentClient studentClient;

    public void cleanStudentCacheById(Long studentId) {
        try {
            studentClient.cleanStudentCacheById(studentId);
        } catch (FeignException e) {
            log.warning("===== FeignException -> " + e.getMessage() + " =====");
            log.warning("===== Error when deleting a cache of student with id " + studentId + " =====");
            throw new ServiceUnavailableException("Error when deleting a cache of student with id " + studentId);
        }
    }
}
