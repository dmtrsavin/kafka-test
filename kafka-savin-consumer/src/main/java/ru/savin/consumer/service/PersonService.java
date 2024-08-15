package ru.savin.consumer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.savin.consumer.dto.PersonDto;
import ru.savin.consumer.entity.PersonEntity;
import ru.savin.consumer.mapper.PersonMapper;
import ru.savin.consumer.repository.PersonRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonMapper personMapper;
    private final PersonRepository personRepository;

    public void persistPerson(PersonDto personDto) {
        PersonEntity person = personMapper.mapToEntity(personDto);
        PersonEntity persistPerson = personRepository.save(person);

        log.info("Сохранился человек с данными {}", persistPerson);
    }
}
