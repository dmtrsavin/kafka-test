package ru.savin.producer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.savin.producer.model.Person;
import ru.savin.producer.producer.Producer;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final Producer producer;

    public String createPersonForBlocking(Person person) {
        try {
            return producer.sendMessageForBlock(person);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String createPersonForRetry(Person person) {
        try {
            return producer.sendMessageForRetry(person);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
