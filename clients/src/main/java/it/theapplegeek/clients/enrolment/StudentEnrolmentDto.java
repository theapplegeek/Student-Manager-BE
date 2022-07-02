package it.theapplegeek.clients.enrolment;

import it.theapplegeek.clients.student.StudentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentEnrolmentDto{
    private StudentDto studentDto;
    @PastOrPresent
    private LocalDate createdDate;
}
