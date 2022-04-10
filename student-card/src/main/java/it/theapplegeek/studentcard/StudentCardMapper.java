package it.theapplegeek.studentcard;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface StudentCardMapper {
    StudentCardDto toDto(StudentCard studentCard);

    //@Mapping(defaultExpression = "java(java.time.LocalDate.now())", target = "createdDate")
    StudentCard toEntity(StudentCardDto studentCardDto);
}
