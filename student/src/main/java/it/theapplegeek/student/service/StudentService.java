package it.theapplegeek.student.service;

import feign.FeignException;
import it.theapplegeek.clients.student.StudentDto;
import it.theapplegeek.clients.studentcard.StudentCardClient;
import it.theapplegeek.clients.studentcard.StudentCardDto;
import it.theapplegeek.shared.exception.BadRequestException;
import it.theapplegeek.shared.exception.NotFoundException;
import it.theapplegeek.shared.util.IsChangedChecker;
import it.theapplegeek.student.mapper.StudentMapper;
import it.theapplegeek.student.model.Student;
import it.theapplegeek.student.repository.StudentRepo;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentCardClient studentCardClient;

    public List<StudentDto> getStudents() {
        return studentRepo.findAll()
                .stream()
                .map((student) -> {
                    StudentCardDto studentCardDto = studentCardClient.getStudentCardByStudentId(student.getId());
                    StudentDto studentDto = studentMapper.toDto(student);
                    if (studentCardDto != null) studentDto.setStudentCardDto(studentCardDto);
                    return studentDto;
                })
                .toList();
    }

    public StudentDto getStudent(Long studentId) {
        return studentRepo.findById(studentId)
                .map((student) -> {
                    StudentCardDto studentCardDto = studentCardClient.getStudentCardByStudentId(student.getId());
                    StudentDto studentDto = studentMapper.toDto(student);
                    if (studentCardDto != null) studentDto.setStudentCardDto(studentCardDto);
                    return studentDto;
                })
                .orElseThrow(() -> new NotFoundException("Student with id " + studentId + " not found"));
    }

    public StudentDto addStudent(StudentDto studentDto) {
        if (studentRepo.existsByEmail(studentDto.getEmail()))
            throw new BadRequestException("Student with email " + studentDto.getEmail() + " is present");
        Student student = studentRepo.save(studentMapper.toEntity(studentDto));
        return studentMapper.toDto(student);
    }

    @Transactional
    public StudentDto updateStudent(Long studentId, StudentDto newStudentDto) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student with id " + studentId + " not found"));
        Student newStudent = studentMapper.toEntity(newStudentDto);
        if (IsChangedChecker.isChanged(student.getFirstName(), newStudent.getFirstName())) {
            student.setFirstName(newStudent.getFirstName());
        }
        if (IsChangedChecker.isChanged(student.getLastName(), newStudent.getLastName())) {
            student.setLastName(newStudent.getLastName());
        }
        if (IsChangedChecker.isChanged(student.getEmail(), newStudent.getEmail())) {
            if (studentRepo.existsByEmail(newStudent.getEmail()))
                // avviene eccezione -> rollback();
                throw new BadRequestException("Email " + student.getEmail() + " is taken");
            student.setEmail(newStudent.getEmail());
        }

        // se non avviene nessun errore -> commit();
        return studentMapper.toDto(studentRepo.save(student));
    }

    public void deleteStudent(Long studentId) {
        if (!studentRepo.existsById(studentId))
            throw new NotFoundException("Student with id " + studentId + " not found");
        studentRepo.deleteById(studentId);
    }
}
