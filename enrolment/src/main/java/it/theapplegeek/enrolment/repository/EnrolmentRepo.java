package it.theapplegeek.enrolment.repository;

import it.theapplegeek.enrolment.model.Enrolment;
import it.theapplegeek.enrolment.model.EnrolmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface EnrolmentRepo extends JpaRepository<Enrolment, EnrolmentId> {
    List<Enrolment> findByStudentId(Long studentId);
    List<Enrolment> findByCourseId(Long courseId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM enrolment WHERE student_id = ?1", nativeQuery = true)
    void deleteAllByStudentId(Long studentId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM enrolment WHERE course_id = ?1", nativeQuery = true)
    void deleteAllByCourseId(Long courseId);
}
