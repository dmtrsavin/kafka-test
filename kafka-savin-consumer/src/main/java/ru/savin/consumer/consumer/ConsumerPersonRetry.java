package ru.savin.consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import ru.savin.consumer.dto.PersonDto;
import ru.savin.consumer.service.PersonService;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsumerPersonRetry {

    private final PersonService personService;
    private final ObjectMapper objectMapper;

    private static final String RETRY = "retry";


    @KafkaListener(id = RETRY, groupId = RETRY, topics = "dima.dima.retry")
    @RetryableTopic(backoff = @Backoff(value = 3000L), attempts = "5",
    autoCreateTopics = "false", include = NullPointerException.class)
    public void consumeMessage(String message) {
        log.info("Сообщение получено {}", message);

        try {
            PersonDto personDto = objectMapper.readValue(message, PersonDto.class);
            personService.persistPerson(personDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @DltHandler
    public void dlt(String in, String topic) {
        log.info(in + " from " + topic);
    }
}
