package it.theapplegeek.studentcard.service;

import it.theapplegeek.clients.studentcard.StudentCardDto;
import it.theapplegeek.shared.exception.BadRequestException;
import it.theapplegeek.shared.exception.NotFoundException;
import it.theapplegeek.shared.util.IsChangedChecker;
import it.theapplegeek.studentcard.feign.StudentFeignClient;
import it.theapplegeek.studentcard.mapper.StudentCardMapper;
import it.theapplegeek.studentcard.model.StudentCard;
import it.theapplegeek.studentcard.repository.StudentCardRepo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

@Log
@Service
public class StudentCardService {

    @Autowired
    private StudentCardRepo studentCardRepo;

    @Autowired
    private StudentCardMapper studentCardMapper;

    @Autowired
    private StudentFeignClient studentClient;

    @Autowired
    private CacheManager cacheManager;

    @Cacheable(value = "student-card", key = "#studentId", sync = true)
    public StudentCardDto getStudentCardByStudentId(Long studentId) {
        return studentCardRepo.findByStudentId(studentId)
                .map((studentCard) -> {
                    log.info("##### Found student card with id " + studentCard.getId() + " of student with is " + studentCard.getStudentId() + " #####");
                    return studentCardMapper.toDto(studentCard);
                })
                .orElseThrow(() -> {
                    log.warning("##### Student card of student with id " + studentId + " not found #####");
                    throw new NotFoundException("Student card of student with id " + studentId + " not found");
                });
    }

    public StudentCardDto addStudentCardAndAssignToStudent(Long studentId, StudentCardDto studentCardDto) {
        if (studentCardRepo.existsByStudentId(studentId)) {
            log.warning("##### Student with id " + studentId + " just have a card #####");
            throw new BadRequestException("Student with id " + studentId + " just have a card");
        }
        studentCardDto.setStudentId(studentId);
        studentCardDto.setCardNumber(studentCardDto.getCardNumber().toUpperCase());
        if (studentCardRepo.existsByCardNumberIgnoreCase(studentCardDto.getCardNumber())) {
            log.warning("##### Card with number " + studentCardDto.getCardNumber() + " is taken #####");
            throw new BadRequestException("Card with number " + studentCardDto.getCardNumber() + " is taken");
        }
        studentClient.cleanStudentCacheById(studentId);
        StudentCard studentCard = studentCardRepo.save(studentCardMapper.toEntity(studentCardDto));
        log.info("##### Card with id " + studentCard.getId() + " created #####");
        return studentCardMapper.toDto(studentCard);
    }

    @Transactional
    public StudentCardDto updateStudentCard(Long cardId, StudentCardDto newStudentCard, Boolean isRenewal) {
        StudentCard studentCard = studentCardRepo.findById(cardId)
                .orElseThrow(() -> {
                    log.warning("##### Card with id " + cardId + " not found #####");
                    throw new NotFoundException("Card with id " + cardId + " not found");
                });
        studentClient.cleanStudentCacheById(studentCard.getStudentId());
        if (IsChangedChecker.isChanged(studentCard.getCardNumber(), newStudentCard.getCardNumber())) {
            log.info("##### Update card number from " + studentCard.getCardNumber() + " to " + newStudentCard.getCardNumber() + " #####");
            studentCard.setCardNumber(newStudentCard.getCardNumber());
        }
        if (IsChangedChecker.isChanged(studentCard.getExpiredDate(), newStudentCard.getExpiredDate())) {
            log.info("##### Update expiration date from " + studentCard.getExpiredDate() + " to " + newStudentCard.getExpiredDate() + " #####");
            studentCard.setExpiredDate(newStudentCard.getExpiredDate());
        }
        if (isRenewal) {
            log.info("##### Is renewal a card #####");
            studentCard.setCreatedDate(LocalDate.now());
        }
        cleanStudentCardCacheByStudentId(studentCard.getStudentId());
        return studentCardMapper.toDto(studentCard);
    }

    public void deleteStudentCard(Long cardId) {
        studentCardRepo.findById(cardId)
                .map(studentCard -> {
                    studentClient.cleanStudentCacheById(studentCard.getStudentId());
                    studentCardRepo.deleteById(cardId);
                    log.info("##### Student card with id " + cardId + " deleted #####");
                    cleanStudentCardCacheByStudentId(studentCard.getStudentId());
                    return studentCard;
                }).orElseThrow(() -> {
                    log.warning("##### Card with id " + cardId + " not found #####");
                    throw new NotFoundException("Card with id " + cardId + " not found");
                });
    }

    @Caching(evict = {
            @CacheEvict(value = "student-card", key = "#studentId")
    })
    @Transactional
    public void deleteStudentCardByStudentId(Long studentId) {
        if (!studentCardRepo.existsByStudentId(studentId)) {
            log.warning("##### Card with id " + studentId + " not found #####");
            throw new NotFoundException("Card with id " + studentId + " not found");
        }
        studentClient.cleanStudentCacheById(studentId);
        studentCardRepo.deleteByStudentId(studentId);
        log.info("##### Student card of student with id " + studentId + " deleted #####");
    }

    public void cleanCaches() {
        Collection<String> items = cacheManager.getCacheNames();
        items.forEach((item) -> {
            Objects.requireNonNull(cacheManager.getCache(item)).clear();
            log.info(String.format("##### deleted cache %s #####", item));
        });
    }

    private void cleanStudentCardCacheByStudentId(Long studentId) {
        Objects.requireNonNull(cacheManager.getCache("student-card")).evict(studentId);
    }
}
