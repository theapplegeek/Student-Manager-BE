package it.theapplegeek.clients.teacher;

import it.theapplegeek.clients.course.CourseDto;
import it.theapplegeek.shared.annotation.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherDto {
    private Long id;
    @Name
    private String firstName;
    @Name
    private String lastName;
    @Name
    private String graduation;
    @Email
    private String email;
}
