package it.theapplegeek.studentcard.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "student_card",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "student_card_number_unique",
                        columnNames = "card_number"
                )
        }
)
public class StudentCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "created_date", nullable = false,
            columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate createdDate;

    @Column(name = "expired_date", nullable = false)
    private LocalDate expiredDate;

    @Transient
    private String validationPeriod;

    @Column(name = "student_id")
    private Long studentId;

    public StudentCard(String cardNumber, LocalDate expiredDate, Long studentId) {
        this.cardNumber = cardNumber;
        this.expiredDate = expiredDate;
        this.studentId = studentId;
    }

    public String getValidationPeriod() {
        if (this.createdDate != null && this.expiredDate != null) {
            Period period = Period.between(this.createdDate, this.expiredDate);
            return String.format("%d years, %d months, %d days", period.getYears(), period.getMonths(), period.getDays());
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StudentCard that = (StudentCard) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
