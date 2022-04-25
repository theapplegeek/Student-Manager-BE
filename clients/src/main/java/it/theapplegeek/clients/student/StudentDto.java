package it.theapplegeek.clients.student;

import it.theapplegeek.clients.studentcard.StudentCardDto;
import it.theapplegeek.shared.annotation.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDto {
    @Min(1)
    private Long id;
    @Name
    private String firstName;
    @Name
    private String lastName;
    @Past
    private LocalDate dob;
    @Email
    private String email;
    @Null
    private Integer age;
    @Null
    private StudentCardDto studentCardDto;

    // for add new student without card
    public StudentDto(String firstName, String lastName, LocalDate dob, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;
    }

    // for add new student with new card
    public StudentDto(String firstName, String lastName, LocalDate dob, String email, StudentCardDto studentCardDto) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;
        this.studentCardDto = studentCardDto;
    }
}
