package it.theapplegeek.studentcard.service;

import it.theapplegeek.clients.student.StudentClient;
import it.theapplegeek.clients.student.StudentDto;
import it.theapplegeek.clients.studentcard.StudentCardDto;
import it.theapplegeek.shared.exception.BadRequestException;
import it.theapplegeek.shared.exception.NotFoundException;
import it.theapplegeek.studentcard.model.StudentCard;
import it.theapplegeek.studentcard.mapper.StudentCardMapper;
import it.theapplegeek.studentcard.repository.StudentCardRepo;
import it.theapplegeek.shared.util.IsChangedChecker;
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

    @Autowired
    private StudentClient studentClient;

    public StudentCardDto getStudentCardByStudentId(Long studentId) {
        return studentCardRepo.findByStudentId(studentId)
                .map((studentCard) -> studentCardMapper.toDto(studentCard))
                .orElseThrow(() ->
                        new NotFoundException("student card with student id " + studentId + " not found")
                );
    }

    public StudentCardDto addStudentCardAndAssignToStudent(Long studentId, StudentCardDto studentCardDto) {
        if (studentCardRepo.existsByStudentId(studentId))
            throw new BadRequestException("student with id " + studentId + " just have a card");
        StudentDto studentDto = studentClient.getStudent(studentId);
        studentCardDto.setStudentId(studentId);
        studentCardDto.setCardNumber(studentCardDto.getCardNumber().toUpperCase());
        if (studentCardRepo.existsByCardNumberIgnoreCase(studentCardDto.getCardNumber()))
            throw new BadRequestException("card with number " + studentCardDto.getCardNumber() + " is present");
        StudentCard studentCard = studentCardRepo.save(studentCardMapper.toEntity(studentCardDto));
        return studentCardMapper.toDto(studentCard);
    }

    @Transactional
    public StudentCardDto updateStudentCard(Long cardId, StudentCardDto newStudentCard, Boolean isRenewal) {
        StudentCard studentCard = studentCardRepo.findById(cardId)
                .orElseThrow(() -> new NotFoundException("card with id " + cardId + " not found"));
        if (IsChangedChecker.isChanged(studentCard.getCardNumber(), newStudentCard.getCardNumber()))
            studentCard.setCardNumber(newStudentCard.getCardNumber());
        if (IsChangedChecker.isChanged(studentCard.getExpiredDate(), newStudentCard.getExpiredDate()))
            studentCard.setExpiredDate(newStudentCard.getExpiredDate());
        if (isRenewal)
            studentCard.setCreatedDate(LocalDate.now());
        return studentCardMapper.toDto(studentCard);
    }

    public void deleteStudentCard(Long cardId) {
        if (!studentCardRepo.existsById(cardId))
            throw new NotFoundException("card with id " + cardId + " not found");
        studentCardRepo.deleteById(cardId);
    }

    public void deleteStudentCardByStudentId(Long studentId) {
        if (!studentCardRepo.existsByStudentId(studentId))
            throw new NotFoundException("card with id " + studentId + " not found");
        studentCardRepo.deleteByStudentId(studentId);
    }
}
