package it.theapplegeek.enrolment.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "enrolment")
public class Enrolment {

    @EmbeddedId
    private EnrolmentId id;

    @Column(name = "created_date", nullable = false,
            columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate createdDate = LocalDate.now();

    public Enrolment(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Enrolment enrolment = (Enrolment) o;
        return id != null && Objects.equals(id, enrolment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
