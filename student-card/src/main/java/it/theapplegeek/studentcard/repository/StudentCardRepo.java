package it.theapplegeek.studentcard.repository;

import it.theapplegeek.studentcard.model.StudentCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface StudentCardRepo extends JpaRepository<StudentCard, Long> {
    Optional<StudentCard> findByStudentId(Long studentId);

    Boolean existsByCardNumberIgnoreCase(String cardNumber);

    boolean existsByStudentId(Long studentId);

    void deleteByStudentId(Long studentId);
}
