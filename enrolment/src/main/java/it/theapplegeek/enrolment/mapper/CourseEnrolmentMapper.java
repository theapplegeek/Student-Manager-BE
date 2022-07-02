package it.theapplegeek.enrolment.mapper;

import it.theapplegeek.clients.enrolment.CourseEnrolmentDto;
import it.theapplegeek.enrolment.model.Enrolment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseEnrolmentMapper {
    CourseEnrolmentDto toDto(Enrolment enrolment);
}
