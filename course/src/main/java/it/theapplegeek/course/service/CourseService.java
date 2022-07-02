package it.theapplegeek.course.service;

import it.theapplegeek.clients.course.CourseDto;
import it.theapplegeek.course.feign.EnrolmentFeignClient;
import it.theapplegeek.course.mapper.CourseMapper;
import it.theapplegeek.course.model.Course;
import it.theapplegeek.course.repository.CourseRepo;
import it.theapplegeek.shared.exception.NotFoundException;
import it.theapplegeek.shared.util.IsChangedChecker;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log
@Service
public class CourseService {

  @Autowired private CourseRepo courseRepo;

  @Autowired private CourseMapper courseMapper;

  @Autowired private EnrolmentFeignClient enrolmentFeignClient;

  public List<CourseDto> getCourses() {
    return courseRepo.findAll().stream()
        .map(
            (course) -> {
              log.info("##### Found course with id " + course.getId() + " #####");
              return courseMapper.toDto(course);
            })
        .toList();
  }

  public CourseDto getCourse(Long courseId) {
    return courseRepo
        .findById(courseId)
        .map(
            (course) -> {
              log.info("##### Found course with id " + course.getId() + " #####");
              return courseMapper.toDto(course);
            })
        .orElseThrow(
            () -> {
              log.warning("##### Course with id " + courseId + " not found #####");
              throw new NotFoundException("course with id " + courseId + " not found");
            });
  }

  public CourseDto addCourse(CourseDto courseDto) {
    Course course = courseRepo.save(courseMapper.toEntity(courseDto));
    log.info("##### Course with id " + course.getId() + " created #####");
    return courseMapper.toDto(course);
  }

  @Transactional
  public CourseDto updateCourse(Long courseId, CourseDto newCourse) {
    Course course =
        courseRepo
            .findById(courseId)
            .orElseThrow(
                () -> {
                  log.warning("##### Course with id " + courseId + " not found #####");
                  throw new NotFoundException("course with id " + courseId + " not found");
                });

    if (IsChangedChecker.isChanged(course.getName(), newCourse.getName()))
      course.setName(newCourse.getName());
    if (IsChangedChecker.isChanged(course.getDepartment(), newCourse.getDepartment()))
      course.setDepartment(newCourse.getDepartment());
    if (IsChangedChecker.isChanged(course.getTeacherId(), newCourse.getTeacherId()))
      course.setTeacherId(newCourse.getTeacherId());

    return courseMapper.toDto(course);
  }

  public void assignTeacherToCourse(Long courseId, Long teacherId) {
    Course course =
        courseRepo
            .findById(courseId)
            .orElseThrow(
                () -> {
                  log.warning("##### Course with id " + courseId + " not found #####");
                  throw new NotFoundException("course with id " + courseId + " not found");
                });
    //        if (teacherRepo.existsById(teacherId))
    //            throw new NotFoundException("teacher with id " + teacherId + " not found");
    //        if (course.getTeacherId() != null)
    //            throw new BadRequestException("course with id " + courseId
    //                    + " just have a teacher " + course.getTeacher().getLastName());
    course.setTeacherId(teacherId);
    Course courseUpdated = courseRepo.save(course);
    log.info(
        "##### Teacher with id "
            + teacherId
            + " is assigned to course with id "
            + courseUpdated.getId()
            + " #####");
  }

  @Transactional
  public void detachTeacherFromAllCourses(Long teacherId) {
    courseRepo
        .findByTeacherId(teacherId)
        .forEach(
            (course) -> {
              course.setTeacherId(null);
              log.info("##### course with id " + course.getId() + " detached a teacher #####");
            });
  }

  public void deleteCourse(Long courseId) {
    if (!courseRepo.existsById(courseId)) {
      log.warning("##### Course with id " + courseId + " not found #####");
      throw new NotFoundException("course with id " + courseId + " not found");
    }
    enrolmentFeignClient.deleteEnrolmentByCourse(courseId);
    log.info("##### Enrolment of course with id " + courseId + " deleted #####");
    courseRepo.deleteById(courseId);
    log.info("##### Course with id " + courseId + " deleted #####");
  }
}
