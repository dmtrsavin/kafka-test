package ru.savin.consumer.mapper;

import org.mapstruct.Mapper;
import ru.savin.consumer.dto.PersonDto;
import ru.savin.consumer.entity.PersonEntity;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonDto mapToDto(PersonEntity person);

    PersonEntity mapToEntity(PersonDto personDto);
}
