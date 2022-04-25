package it.theapplegeek.student.controller;

import it.theapplegeek.clients.student.StudentDto;
import it.theapplegeek.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<StudentDto> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("{studentId}")
    public StudentDto getStudent(@PathVariable("studentId") Long studentId) {
        return studentService.getStudent(studentId);
    }

    @PostMapping
    public StudentDto addStudent(@Valid @RequestBody StudentDto studentDto) {
        return studentService.addStudent(studentDto);
    }

    @PutMapping("{studentId}")
    public StudentDto updateStudent(@PathVariable("studentId") Long studentId,
                                    @RequestBody StudentDto studentDto) {
        return studentService.updateStudent(studentId, studentDto);
    }

    @DeleteMapping("{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }
}
