package it.theapplegeek.studentcard.controller;

import it.theapplegeek.studentcard.model.StudentCard;
import it.theapplegeek.studentcard.dto.StudentCardDto;
import it.theapplegeek.studentcard.service.StudentCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/student_card")
public class StudentCardController {

    @Autowired
    private StudentCardService studentCardService;

    @GetMapping("student/{studentId}")
    public StudentCardDto getStudentCardByStudentId(@PathVariable("studentId") Long studentId) {
        return studentCardService.getStudentCardByStudentId(studentId);
    }

    @PostMapping("student/{studentId}")
    public StudentCardDto addStudentCardAndAssignToStudent(@PathVariable("studentId") Long studentId,
                                                           @Valid @RequestBody StudentCard studentCard) {
        return studentCardService.addStudentCardAndAssignToStudent(studentId, studentCard);
    }

    @PutMapping("{cardId}")
    public StudentCardDto updateStudentCard(@PathVariable("cardId") Long cardId,
                                            @RequestParam(value = "isRenewal",
                                                    required = false,
                                                    defaultValue = "false") Boolean isRenewal,
                                            @RequestBody StudentCard studentCard) {
        return studentCardService.updateStudentCard(cardId, studentCard, isRenewal);
    }

    @DeleteMapping("{cardId}")
    public void deleteStudentCard(@PathVariable("cardId") Long cardId) {
        studentCardService.deleteStudentCard(cardId);
    }
}
