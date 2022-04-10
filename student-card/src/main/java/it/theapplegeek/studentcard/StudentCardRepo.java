package it.theapplegeek.studentcard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentCardRepo extends JpaRepository<StudentCard, Long> {
    Optional<StudentCard> findByStudentId(Long studentId);
    Boolean existsByCardNumberIgnoreCase(String cardNumber);
}
