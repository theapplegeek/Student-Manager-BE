package it.theapplegeek.student.mapper;

import it.theapplegeek.clients.student.StudentDto;
import it.theapplegeek.student.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {
//    @Mapping(source = "studentCard", target = "studentCardDto")
    StudentDto toDto(Student student);

//    @Mapping(source = "studentCardDto", target = "studentCard")
//    @Mapping(source = "studentCardDto.createdDate", target = "studentCard.createdDate",
//            defaultExpression = "java(java.time.LocalDate.now())")
    Student toEntity(StudentDto studentDto);
}
