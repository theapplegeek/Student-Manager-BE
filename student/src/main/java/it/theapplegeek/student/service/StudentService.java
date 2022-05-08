package it.theapplegeek.student.service;

import feign.FeignException;
import it.theapplegeek.clients.student.StudentDto;
import it.theapplegeek.clients.studentcard.StudentCardClient;
import it.theapplegeek.clients.studentcard.StudentCardDto;
import it.theapplegeek.shared.exception.BadRequestException;
import it.theapplegeek.shared.exception.NotFoundException;
import it.theapplegeek.shared.util.IsChangedChecker;
import it.theapplegeek.student.feign.StudentCardFeignClient;
import it.theapplegeek.student.mapper.StudentMapper;
import it.theapplegeek.student.model.Student;
import it.theapplegeek.student.repository.StudentRepo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Log
@Service
@CacheConfig(cacheNames = {"students"})
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentCardFeignClient studentCardClient;

    @Autowired
    private CacheManager cacheManager;

    @Cacheable
    public List<StudentDto> getStudents() {
        return studentRepo.findAll()
                .stream()
                .map((student) -> {
                    StudentCardDto studentCardDto = studentCardClient.getStudentCardByStudentId(student.getId());
                    StudentDto studentDto = studentMapper.toDto(student);
                    if (studentCardDto != null) studentDto.setStudentCardDto(studentCardDto);
                    log.info("===== Found student with id " + student.getId() + " =====");
                    return studentDto;
                })
                .toList();
    }

    @Cacheable(value = "student", key = "#studentId", sync = true)
    public StudentDto getStudent(Long studentId) {
        return studentRepo.findById(studentId)
                .map((student) -> {
                    StudentCardDto studentCardDto = studentCardClient.getStudentCardByStudentId(student.getId());
                    StudentDto studentDto = studentMapper.toDto(student);
                    if (studentCardDto != null) studentDto.setStudentCardDto(studentCardDto);
                    log.info("===== Found student with id " + student.getId() + " =====");
                    return studentDto;
                })
                .orElseThrow(() -> {
                    log.warning("===== Student with id " + studentId + " not found =====");
                    return new NotFoundException("Student with id " + studentId + " not found");
                });
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "students", allEntries = true)
    })
    public StudentDto addStudent(StudentDto studentDto) {
        if (studentRepo.existsByEmail(studentDto.getEmail())) {
            log.warning("===== Student with email " + studentDto.getEmail() + " is taken =====");
            throw new BadRequestException("Student with email " + studentDto.getEmail() + " is taken");
        }
        Student student = studentRepo.save(studentMapper.toEntity(studentDto));
        log.info("===== Student with id " + student.getId() + " created =====");
        return studentMapper.toDto(student);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "student", key = "#studentId")
    })
    @Transactional
    public StudentDto updateStudent(Long studentId, StudentDto newStudentDto) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> {
                    log.warning("===== Student with id " + studentId + " not found =====");
                    return new NotFoundException("Student with id " + studentId + " not found");
                });
        Student newStudent = studentMapper.toEntity(newStudentDto);
        if (IsChangedChecker.isChanged(student.getFirstName(), newStudent.getFirstName())) {
            log.info("===== Update firstName from " + student.getFirstName() + " to " + newStudent.getFirstName() + " =====");
            student.setFirstName(newStudent.getFirstName());
        }
        if (IsChangedChecker.isChanged(student.getLastName(), newStudent.getLastName())) {
            log.info("===== Update lastName from " + student.getLastName() + " to " + newStudent.getLastName() + " =====");
            student.setLastName(newStudent.getLastName());
        }
        if (IsChangedChecker.isChanged(student.getEmail(), newStudent.getEmail())) {
            log.info("===== Update mail from " + student.getEmail() + " to " + newStudent.getEmail() + " =====");
            if (studentRepo.existsByEmail(newStudent.getEmail())) {
                log.warning("===== Email " + student.getEmail() + " is taken =====");
                throw new BadRequestException("Email " + student.getEmail() + " is taken");
            }
            student.setEmail(newStudent.getEmail());
        }
        Student updatedStudent = studentRepo.save(student);
        log.info("===== Student with id " + student.getId() + " updated =====");
        return studentMapper.toDto(updatedStudent);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "students", allEntries = true),
            @CacheEvict(cacheNames = "student", key = "#studentId")
    })
    public void deleteStudent(Long studentId) {
        if (!studentRepo.existsById(studentId)) {
            log.warning("===== Student with id " + studentId + " not found =====");
            throw new NotFoundException("Student with id " + studentId + " not found");
        }
        studentCardClient.deleteStudentCard(studentId);
        log.info("===== Student card of student with id " + studentId + " deleted =====");
        studentRepo.deleteById(studentId);
        log.info("===== Student with id " + studentId + " deleted =====");
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "students", allEntries = true),
            @CacheEvict(cacheNames = "student", key = "#studentId")
    })
    public void cleanStudentCacheById(Long studentId) {
    }

    public void cleanCaches() {
        Collection<String> items = cacheManager.getCacheNames();
        items.forEach((item) -> {
            Objects.requireNonNull(cacheManager.getCache(item)).clear();
            log.info(String.format("===== deleted cache %s =====", item));
        });
    }
}
