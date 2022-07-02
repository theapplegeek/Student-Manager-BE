package it.theapplegeek.teacher.mapper;

import it.theapplegeek.clients.teacher.TeacherDto;
import it.theapplegeek.teacher.model.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    TeacherDto toDto(Teacher teacher);
    Teacher toEntity(TeacherDto teacherDto);
}
