package it.theapplegeek.enrolment.service;

import it.theapplegeek.clients.course.CourseDto;
import it.theapplegeek.clients.enrolment.CourseEnrolmentDto;
import it.theapplegeek.clients.enrolment.StudentEnrolmentDto;
import it.theapplegeek.clients.student.StudentDto;
import it.theapplegeek.enrolment.feign.CourseFeignClient;
import it.theapplegeek.enrolment.feign.StudentFeignClient;
import it.theapplegeek.enrolment.mapper.CourseEnrolmentMapper;
import it.theapplegeek.enrolment.mapper.StudentEnrolmentMapper;
import it.theapplegeek.enrolment.model.Enrolment;
import it.theapplegeek.enrolment.model.EnrolmentId;
import it.theapplegeek.enrolment.repository.EnrolmentRepo;
import it.theapplegeek.shared.exception.NotFoundException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.nio.file.Watchable;
import java.time.LocalDate;
import java.util.List;

@Log
@Service
@CacheConfig(cacheNames = {"enrolments"})
public class EnrolmentService {

  @Autowired private EnrolmentRepo enrolmentRepo;

  @Autowired private CourseEnrolmentMapper courseEnrolmentMapper;

  @Autowired private StudentEnrolmentMapper studentEnrolmentMapper;

  @Autowired private StudentFeignClient studentFeignClient;

  @Autowired private CourseFeignClient courseFeignClient;

  @Cacheable(value = "student-enrolment", key = "#studentId", sync = true)
  public List<CourseEnrolmentDto> getCoursesByStudent(Long studentId) {
    return enrolmentRepo.findByStudentId(studentId).stream()
        .map(
            (enrolment) -> {
              CourseDto courseDto = courseFeignClient.getCourse(enrolment.getId().getCourseId());
              if (courseDto == null)
                throw new NotFoundException(
                    "Error course with id " + enrolment.getId().getCourseId() + " not found");
              log.info("##### Found course with id " + courseDto.getId() + " #####");
              CourseEnrolmentDto courseEnrolmentDto = courseEnrolmentMapper.toDto(enrolment);
              courseEnrolmentDto.setCourseDto(courseDto);
              return courseEnrolmentDto;
            })
        .toList();
  }

  @Cacheable(value = "course-enrolment", key = "#courseId", sync = true)
  public List<StudentEnrolmentDto> getStudentsByCourse(Long courseId) {
    return enrolmentRepo.findByCourseId(courseId).stream()
        .map(
            (enrolment) -> {
              StudentDto studentDto =
                  studentFeignClient.getStudent(enrolment.getId().getStudentId());
              if (studentDto == null)
                throw new NotFoundException(
                    "Error student with id " + enrolment.getId().getStudentId() + " not found");
              log.info("##### Found student with id " + studentDto.getId() + " #####");
              StudentEnrolmentDto studentEnrolmentDto = studentEnrolmentMapper.toDto(enrolment);
              studentEnrolmentDto.setStudentDto(studentDto);
              return studentEnrolmentDto;
            })
        .toList();
  }

  @Caching(
      evict = {
        @CacheEvict(cacheNames = "course-enrolment", key = "#enrolmentId.courseId"),
        @CacheEvict(cacheNames = "student-enrolment", key = "#enrolmentId.studentId")
      })
  public void addEnrolment(EnrolmentId enrolmentId, LocalDate createdDate) {
    StudentDto studentDto = studentFeignClient.getStudent(enrolmentId.getStudentId());
    if (studentDto == null)
      throw new NotFoundException(
          "Error student with id " + enrolmentId.getStudentId() + " not found");
    CourseDto courseDto = courseFeignClient.getCourse(enrolmentId.getCourseId());
    if (courseDto == null)
      throw new NotFoundException(
          "Error course with id " + enrolmentId.getCourseId() + " not found");
    Enrolment enrolment = Enrolment.builder().id(enrolmentId).createdDate(createdDate).build();
    Enrolment enrolmentSaved = enrolmentRepo.save(enrolment);
    log.info(
        "##### Enrolment with student id "
            + enrolmentSaved.getId().getStudentId()
            + " and course id "
            + enrolmentSaved.getId().getCourseId()
            + " created #####");
  }

  @Caching(
      evict = {
        @CacheEvict(cacheNames = "course-enrolment", key = "#courseId"),
        @CacheEvict(cacheNames = "student-enrolment", key = "#studentId")
      })
  public void deleteEnrolment(Long studentId, Long courseId) {
    EnrolmentId enrolmentId = EnrolmentId.builder().studentId(studentId).courseId(courseId).build();
    enrolmentRepo.deleteById(enrolmentId);
    log.info(
        "##### Enrolment with student id "
            + studentId
            + " and course id "
            + courseId
            + " deleted #####");
  }

  @Caching(
      evict = {
        @CacheEvict(cacheNames = "course-enrolment", allEntries = true),
        @CacheEvict(cacheNames = "student-enrolment", key = "#studentId")
      })
  public void deleteEnrolmentByStudent(Long studentId) {
    enrolmentRepo.deleteAllByStudentId(studentId);
    log.info("##### All enrolments with student id " + studentId + " are deleted #####");
  }

  @Caching(
      evict = {
        @CacheEvict(cacheNames = "course-enrolment", key = "#courseId"),
        @CacheEvict(cacheNames = "student-enrolment", allEntries = true)
      })
  public void deleteEnrolmentByCourse(Long courseId) {
    enrolmentRepo.deleteAllByCourseId(courseId);
    log.info("##### All enrolments with course id " + courseId + " are deleted #####");
  }
}
