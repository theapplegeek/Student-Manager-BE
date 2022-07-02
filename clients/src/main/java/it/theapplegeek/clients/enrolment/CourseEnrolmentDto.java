package it.theapplegeek.clients.enrolment;

import it.theapplegeek.clients.course.CourseDto;
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
public class CourseEnrolmentDto {
    private CourseDto courseDto;
    @PastOrPresent
    private LocalDate createdDate;
}
