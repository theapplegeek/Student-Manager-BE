package it.theapplegeek.studentcard.mapper;

import it.theapplegeek.studentcard.model.StudentCard;
import it.theapplegeek.studentcard.dto.StudentCardDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentCardMapper {
    StudentCardDto toDto(StudentCard studentCard);

    @Mapping(defaultExpression = "java(java.time.LocalDate.now())", target = "createdDate")
    StudentCard toEntity(StudentCardDto studentCardDto);
}
