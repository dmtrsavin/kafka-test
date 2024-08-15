package ru.savin.producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.savin.producer.model.Person;

@Slf4j
@Component
public class Producer {

    private static final String BLOCK_TOPIC = "dima.dima.block";
    private static final String RETRY_TOPIC = "dima.dima.retry";

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;


    public Producer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public String sendMessageForBlock(Person person) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(person);

        message = null;
        kafkaTemplate.send(BLOCK_TOPIC, message);

        log.info("Информация о человеке была отправлена {}", message);

        return "Сообщение отправлено";
    }

    public String sendMessageForRetry(Person person) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(person);

        message = null;
        kafkaTemplate.send(RETRY_TOPIC, message);

        log.info("Информация о человеке была отправлена {}", message);

        return "Сообщение отправлено";
    }
}
