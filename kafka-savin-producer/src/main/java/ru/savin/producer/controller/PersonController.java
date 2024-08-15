package ru.savin.producer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.savin.producer.model.Person;
import ru.savin.producer.service.PersonService;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping
    public String createPerson(@RequestBody Person person) {
        return personService.createPersonForBlocking(person);
    }

    @PostMapping("/r")
    public String createPersonForRetry(@RequestBody Person person) {
        return personService.createPersonForRetry(person);
    }
}
