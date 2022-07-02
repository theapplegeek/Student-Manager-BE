package it.theapplegeek.enrolment.mapper;

import it.theapplegeek.clients.enrolment.StudentEnrolmentDto;
import it.theapplegeek.enrolment.model.Enrolment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentEnrolmentMapper {
    StudentEnrolmentDto toDto(Enrolment enrolment);
}
