package it.theapplegeek.course.repository;

import it.theapplegeek.course.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
    List<Course> findByTeacherId(Long teacherId);
}
