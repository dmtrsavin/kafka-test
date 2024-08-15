package ru.savin.consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

import java.util.Map;

@EnableKafka
@Configuration
public class ConsumerConfig {

    private static final String BLOCK = "block";

    @Value(value = "${kafka.backoff.interval}")
    private long interval;

    @Value(value = "${kafka.backoff.max_failure}")
    private long maxAttempts;

    private final KafkaProperties kafkaProperties;

    public ConsumerConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    public ConsumerFactory<String, Object> consumerFactory(String groupId) {
        Map<String, Object> properties = kafkaProperties.buildConsumerProperties(new DefaultSslBundleRegistry());
        properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return new DefaultKafkaConsumerFactory<>(properties);
    }

    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(String groupId) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.consumerFactory(groupId));

        if (BLOCK.equals(groupId)) {
            factory.setCommonErrorHandler(errorHandler());
            factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        }
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> blockingKafkaListenerContainerFactory() {
        return kafkaListenerContainerFactory(BLOCK);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> retryKafkaListenerContainerFactory() {
        return kafkaListenerContainerFactory("retry");
    }

    //Блокирующая повторная попытка
    @Bean
    public DefaultErrorHandler errorHandler() {
        BackOff backoff = new FixedBackOff(interval, maxAttempts);
        DefaultErrorHandler errorHandler = new DefaultErrorHandler((consumerRecord, exception) -> {
            System.out.println(String.format("Запись удалена %s из-за исключения", consumerRecord.toString(),exception.getClass().getName()));
        }, backoff);

        errorHandler.addNotRetryableExceptions(NullPointerException.class);
        return errorHandler;
    }
}
