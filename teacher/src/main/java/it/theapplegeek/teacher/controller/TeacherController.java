package it.theapplegeek.teacher.controller;

import it.theapplegeek.clients.teacher.TeacherDto;
import it.theapplegeek.shared.util.JsonUtils;
import it.theapplegeek.teacher.service.TeacherService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Log
@RestController
@RequestMapping("api/v1/teacher")
public class TeacherController {

  @Autowired private TeacherService teacherService;

  @Autowired private JsonUtils jsonUtils;

  @GetMapping
  public List<TeacherDto> getTeachers() {
    log.info("##### GET getTeachers -> get all teachers #####");
    return teacherService.getTeachers();
  }

  @GetMapping("{teacherId}")
  public TeacherDto getTeacher(@PathVariable("teacherId") Long teacherId) {
    log.info("##### GET getTeacher -> get teacher with id " + teacherId + " #####");
    return teacherService.getTeacher(teacherId);
  }

  @PostMapping
  public TeacherDto addTeacher(@Valid @RequestBody TeacherDto teacherDto) {
    log.info("##### POST addTeacher -> add a new teacher #####");
    log.info("##### teacherDto: \n" + jsonUtils.convertObjectToPrettyJson(teacherDto) + "\n#####");
    return teacherService.addTeacher(teacherDto);
  }

  @PutMapping("{teacherId}")
  public TeacherDto updateTeacher(
      @PathVariable("teacherId") Long teacherId, @RequestBody TeacherDto teacherDto) {
    log.info("##### PUT updateTeacher -> update teacher with id " + teacherId + " #####");
    log.info("##### teacherDto: \n" + jsonUtils.convertObjectToPrettyJson(teacherDto) + "\n#####");
    TeacherDto teacherUpdated = teacherService.updateTeacher(teacherId, teacherDto);
    log.info("##### Teacher with id " + teacherUpdated.getId() + " updated #####");
    return teacherUpdated;
  }

  @DeleteMapping("{teacherId}")
  public void deleteTeacher(@PathVariable("teacherId") Long teacherId) {
    log.info("##### DELETE deleteTeacher -> delete teacher with id " + teacherId + " #####");
    teacherService.deleteTeacher(teacherId);
  }
}
