package it.theapplegeek.student.controller;

import it.theapplegeek.clients.student.StudentDto;
import it.theapplegeek.shared.util.JsonUtils;
import it.theapplegeek.student.service.StudentService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Log
@RestController
@RequestMapping("api/v1/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private JsonUtils jsonUtils;

    @GetMapping
    public List<StudentDto> getStudents() {
        log.info("##### GET getStudents -> get all students #####");
        return studentService.getStudents();
    }

    @GetMapping("{studentId}")
    public StudentDto getStudent(@PathVariable("studentId") Long studentId) {
        log.info("##### GET getStudent -> get student with id " + studentId + " #####");
        return studentService.getStudent(studentId);
    }

    @PostMapping
    public StudentDto addStudent(@Valid @RequestBody StudentDto studentDto) {
        log.info("##### POST addStudent -> add a new student #####");
        log.info("##### studentDto: \n" + jsonUtils.convertObjectToPrettyJson(studentDto) + "\n#####");
        return studentService.addStudent(studentDto);
    }

    @PutMapping("{studentId}")
    public StudentDto updateStudent(@PathVariable("studentId") Long studentId,
                                    @RequestBody StudentDto studentDto) {
        log.info("##### PUT updateStudent -> update a existing student with id " + studentId + " #####");
        log.info("##### studentDto: \n" + jsonUtils.convertObjectToPrettyJson(studentDto) + "\n#####");
        return studentService.updateStudent(studentId, studentDto);
    }

    @DeleteMapping("{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        log.info("##### DELETE deleteStudent -> delete a existing student with id " + studentId + " #####");
        studentService.deleteStudent(studentId);
    }

    @DeleteMapping("cache/delete/{studentId}")
    public void cleanStudentCacheById(@PathVariable("studentId") Long studentId) {
        log.info("##### DELETE cleanStudentCacheById -> clean cache of student with id " + studentId + " #####");
        this.studentService.cleanStudentCacheById(studentId);
    }

    @DeleteMapping("cache/delete")
    public void cleanCache() {
        log.info("##### DELETE cleancache -> clean all caches #####");
        this.studentService.cleanCaches();
    }
}
