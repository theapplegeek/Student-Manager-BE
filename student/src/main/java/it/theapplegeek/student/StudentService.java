package it.theapplegeek.student;

import it.theapplegeek.exception.BadRequestException;
import it.theapplegeek.exception.NotFoundException;
import it.theapplegeek.util.IsChangedChecker;
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

    public List<StudentDto> getStudents() {
        return studentRepo.findAll()
                .stream()
                .map((student) -> studentMapper.toDto(student))
                .toList();
    }

    public StudentDto getStudent(Long studentId) {
        return studentRepo.findById(studentId)
                .map((student -> studentMapper.toDto(student)))
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
