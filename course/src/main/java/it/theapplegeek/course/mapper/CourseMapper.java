package it.theapplegeek.course.mapper;

import it.theapplegeek.clients.course.CourseDto;
import it.theapplegeek.course.model.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseDto toDto(Course course);
    Course toEntity(CourseDto courseDto);
}
