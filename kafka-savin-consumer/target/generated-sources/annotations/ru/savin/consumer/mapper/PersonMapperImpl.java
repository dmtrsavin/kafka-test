package ru.savin.consumer.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.savin.consumer.dto.PersonDto;
import ru.savin.consumer.entity.PersonEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-15T12:11:06+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.11 (BellSoft)"
)
@Component
public class PersonMapperImpl implements PersonMapper {

    @Override
    public PersonDto mapToDto(PersonEntity person) {
        if ( person == null ) {
            return null;
        }

        PersonDto personDto = new PersonDto();

        personDto.setName( person.getName() );
        personDto.setPrice( person.getPrice() );

        return personDto;
    }

    @Override
    public PersonEntity mapToEntity(PersonDto personDto) {
        if ( personDto == null ) {
            return null;
        }

        PersonEntity personEntity = new PersonEntity();

        personEntity.setName( personDto.getName() );
        personEntity.setPrice( personDto.getPrice() );

        return personEntity;
    }
}
