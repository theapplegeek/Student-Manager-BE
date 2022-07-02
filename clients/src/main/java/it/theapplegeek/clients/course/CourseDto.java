package it.theapplegeek.clients.course;

import it.theapplegeek.shared.annotation.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDto {
    @Null
    private Long id;
    @Name
    private String name;
    @Name
    private String department;
    private Long teacherId;
}
