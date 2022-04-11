package it.theapplegeek.student.repository;


import it.theapplegeek.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
    Boolean existsByEmail(String email);
}
