package it.theapplegeek.studentcard.controller;

import it.theapplegeek.clients.studentcard.StudentCardDto;
import it.theapplegeek.shared.util.JsonUtils;
import it.theapplegeek.studentcard.model.StudentCard;
import it.theapplegeek.studentcard.service.StudentCardService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log
@RestController
@RequestMapping("api/v1/student_card")
public class StudentCardController {

    @Autowired
    private StudentCardService studentCardService;

    @Autowired
    private JsonUtils jsonUtils;

    @GetMapping("student/{studentId}")
    public StudentCardDto getStudentCardByStudentId(@PathVariable("studentId") Long studentId) {
        log.info("===== GET getStudentCardByStudentId -> get student card of student with id " + studentId + " =====");
        return studentCardService.getStudentCardByStudentId(studentId);
    }

    @PostMapping("student/{studentId}")
    public StudentCardDto addStudentCardAndAssignToStudent(@PathVariable("studentId") Long studentId,
                                                           @Valid @RequestBody StudentCardDto studentCardDto) {
        log.info("===== POST addStudentCardAndAssignToStudent -> add student card of student with id " + studentId + " =====");
        log.info("===== studentCardDto: \n" + jsonUtils.convertObjectToPrettyJson(studentCardDto) + "\n=====");
        return studentCardService.addStudentCardAndAssignToStudent(studentId, studentCardDto);
    }

    @PutMapping("{cardId}")
    public StudentCardDto updateStudentCard(@PathVariable("cardId") Long cardId,
                                            @RequestParam(value = "isRenewal",
                                                    required = false,
                                                    defaultValue = "false") Boolean isRenewal,
                                            @RequestBody StudentCardDto studentCardDto) {
        log.info("===== PUT updateStudentCard -> update student card with id " + cardId + " =====");
        log.info("===== Is a renewal: " + isRenewal + " =====");
        log.info("===== studentCardDto: \n" + jsonUtils.convertObjectToPrettyJson(studentCardDto) + "\n=====");
        return studentCardService.updateStudentCard(cardId, studentCardDto, isRenewal);
    }

    @DeleteMapping("{cardId}")
    public void deleteStudentCard(@PathVariable("cardId") Long cardId) {
        log.info("===== DELETE deleteStudentCard -> delete a student card with id " + cardId + " =====");
        studentCardService.deleteStudentCard(cardId);
    }

    @DeleteMapping("student/{studentId}")
    public void deleteStudentCardByStudentId(@PathVariable("studentId") Long studentId) {
        log.info("===== DELETE deleteStudentCard -> delete a student card of student with id " + studentId + " =====");
        studentCardService.deleteStudentCardByStudentId(studentId);
    }

    @DeleteMapping("delete/cache")
    public void cleancache() {
        log.info("===== DELETE cleancache -> clean all caches =====");
        this.studentCardService.cleanCaches();
    }
}
