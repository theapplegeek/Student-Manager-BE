package it.theapplegeek.teacher.service;

import it.theapplegeek.clients.teacher.TeacherDto;
import it.theapplegeek.shared.exception.NotFoundException;
import it.theapplegeek.shared.util.IsChangedChecker;
import it.theapplegeek.teacher.feign.CourseFeignClient;
import it.theapplegeek.teacher.mapper.TeacherMapper;
import it.theapplegeek.teacher.model.Teacher;
import it.theapplegeek.teacher.repository.TeacherRepo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log
@Service
public class TeacherService {

  @Autowired private TeacherRepo teacherRepo;

  @Autowired private TeacherMapper teacherMapper;

  @Autowired private CourseFeignClient courseFeignClient;

  public List<TeacherDto> getTeachers() {
    return teacherRepo.findAll().stream()
        .map(
            (teacher -> {
              log.info("##### Found teacher with id " + teacher.getId() + " #####");
              return teacherMapper.toDto(teacher);
            }))
        .toList();
  }

  public TeacherDto getTeacher(Long teacherId) {
    return teacherRepo
        .findById(teacherId)
        .map(
            (teacher -> {
              log.info("##### Found teacher with id " + teacher.getId() + " #####");
              return teacherMapper.toDto(teacher);
            }))
        .orElseThrow(
            () -> {
              log.warning("##### Teacher with id " + teacherId + " not found #####");
              throw new NotFoundException("teacher with id " + teacherId + " not found");
            });
  }

  public TeacherDto addTeacher(TeacherDto teacherDto) {
    Teacher teacher = teacherRepo.save(teacherMapper.toEntity(teacherDto));
    log.info("##### Teacher with id " + teacher.getId() + " created #####");
    return teacherMapper.toDto(teacher);
  }

  @Transactional
  public TeacherDto updateTeacher(Long teacherId, TeacherDto newTeacher) {
    Teacher teacher =
        teacherRepo
            .findById(teacherId)
            .orElseThrow(
                () -> {
                  log.warning("##### Teacher with id " + teacherId + " not found #####");
                  throw new NotFoundException("teacher with id " + teacherId + " not found");
                });

    if (IsChangedChecker.isChanged(teacher.getFirstName(), newTeacher.getFirstName()))
      teacher.setFirstName(newTeacher.getFirstName());
    if (IsChangedChecker.isChanged(teacher.getLastName(), newTeacher.getLastName()))
      teacher.setLastName(newTeacher.getLastName());
    if (IsChangedChecker.isChanged(teacher.getEmail(), newTeacher.getEmail()))
      teacher.setEmail(newTeacher.getEmail());
    if (IsChangedChecker.isChanged(teacher.getGraduation(), newTeacher.getGraduation()))
      teacher.setGraduation(newTeacher.getGraduation());

    return teacherMapper.toDto(teacher);
  }

  public void deleteTeacher(Long teacherId) {
    if (teacherRepo.existsById(teacherId)) {
      log.warning("##### Teacher with id " + teacherId + "not found #####");
      throw new NotFoundException("teacher with id " + teacherId + " not found");
    }
    courseFeignClient.detachTeacherFromAllCourses(teacherId);
    log.info("##### Detached teacher with id " + teacherId + " in all courses #####");
    teacherRepo.deleteById(teacherId);
    log.info("##### Teacher with id " + teacherId + " deleted #####");
  }
}
