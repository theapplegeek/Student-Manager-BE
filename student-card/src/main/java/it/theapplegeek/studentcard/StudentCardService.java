package it.theapplegeek.studentcard;

import it.theapplegeek.exception.NotFoundException;
import it.theapplegeek.util.IsChangedChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class StudentCardService {

    @Autowired
    private StudentCardRepo studentCardRepo;

    @Autowired
    private StudentCardMapper studentCardMapper;

    public StudentCardDto getStudentCardByStudentId(Long studentId) {
        return studentCardRepo.findByStudentId(studentId)
                .map((studentCard) -> studentCardMapper.toDto(studentCard))
                .orElseThrow(() ->
                        new NotFoundException("student card with student id " + studentId + " not found")
                );
    }

    public StudentCardDto addStudentCardAndAssignToStudent(Long studentId, StudentCard studentCard) {
//        Student student = studentRepo.findById(studentId)
//                .orElseThrow(() -> new NotFoundException("student with id " + studentId + " not found"));
//        if (student.getStudentCard() != null)
//            throw new BadRequestException("student with id " + studentId + " just have a card with number " + student.getStudentCard().getCardNumber());
//        studentCard.setStudentId(studentId);
//        studentCard.setCardNumber(studentCard.getCardNumber().toUpperCase());
//        if (studentCardRepo.existsByCardNumberIgnoreCase(studentCard.getCardNumber()))
//            throw new BadRequestException("card with number " + studentCard.getCardNumber() + " is present");
//        todo refactor with microservices style
        return studentCardMapper.toDto(studentCardRepo.save(studentCard));
    }

    @Transactional
    public StudentCardDto updateStudentCard(Long cardId, StudentCard newStudentCard, Boolean isRenewval) {
        StudentCard studentCard = studentCardRepo.findById(cardId)
                .orElseThrow(() -> new NotFoundException("card with id " + cardId + " not found"));

        if (IsChangedChecker.isChanged(studentCard.getCardNumber(), newStudentCard.getCardNumber()))
            studentCard.setCardNumber(newStudentCard.getCardNumber());
        if (IsChangedChecker.isChanged(studentCard.getExpiredDate(), newStudentCard.getExpiredDate()))
            studentCard.setExpiredDate(newStudentCard.getExpiredDate());
        if (isRenewval)
            studentCard.setCreatedDate(LocalDate.now());

        return studentCardMapper.toDto(studentCard);
    }

    public void deleteStudentCard(Long cardId) {
        if (!studentCardRepo.existsById(cardId))
            throw new NotFoundException("card with id " + cardId + " not found");
        studentCardRepo.deleteById(cardId);
    }
}
