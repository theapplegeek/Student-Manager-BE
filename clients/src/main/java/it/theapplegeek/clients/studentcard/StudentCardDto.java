package it.theapplegeek.clients.studentcard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentCardDto implements Serializable {
    private Long id;
    @NotNull
    private String cardNumber;
    @PastOrPresent
    private LocalDate createdDate;
    @FutureOrPresent
    @NotNull
    private LocalDate expiredDate;
    @Null
    private String validationPeriod;
    @Min(1)
    private Long studentId;

    public StudentCardDto(String cardNumber, LocalDate createdDate, LocalDate expiredDate, Long studentId) {
        this.cardNumber = cardNumber;
        this.createdDate = createdDate;
        this.expiredDate = expiredDate;
        this.studentId = studentId;
    }

    // for add a new card to existing student
    public StudentCardDto(String cardNumber, LocalDate expiredDate, Long studentId) {
        this.cardNumber = cardNumber;
        this.expiredDate = expiredDate;
        this.studentId = studentId;
    }

    // for add a new card to new student
    public StudentCardDto(String cardNumber, LocalDate expiredDate) {
        this.cardNumber = cardNumber;
        this.expiredDate = expiredDate;
    }

    // for add a new card with custom createdDate to new student
    public StudentCardDto(String cardNumber, LocalDate createdDate, LocalDate expiredDate) {
        this.cardNumber = cardNumber;
        this.createdDate = createdDate;
        this.expiredDate = expiredDate;
    }
}
