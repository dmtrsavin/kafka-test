package ru.savin.consumer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaSavinConsumer
{
    public static void main( String[] args ) {
        SpringApplication.run(KafkaSavinConsumer.class);
    }
}
