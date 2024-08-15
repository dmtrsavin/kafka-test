package ru.savin.consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.savin.consumer.dto.PersonDto;
import ru.savin.consumer.service.PersonService;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsumerPersonBlock {
    private final ObjectMapper objectMapper;
    private final PersonService personService;

    private static final String GROUP_BLOCK = "block";

    @KafkaListener(groupId = GROUP_BLOCK, topics = "dima.dima.block")
    public void consumeMessage(String message) {
        log.info("Сообщение получено {}", message);

        try {
            PersonDto personDto = objectMapper.readValue(message, PersonDto.class);
            personService.persistPerson(personDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
