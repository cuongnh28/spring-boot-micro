package com.demo.controller;

import com.demo.dto.User;
import com.demo.kafka.config.UserKafkaProducer;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

// This class for testing Kafka
@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserKafkaProducer userKafkaProducer;

    @PostMapping()
    public boolean generateUser() {
        Faker faker = new Faker();
        User user = new User(faker.name().username(), faker.internet().emailAddress(), faker.internet().password());
        userKafkaProducer.writeToKafka(user);
        return true;
    }

    @GetMapping()
    public ZonedDateTime getZone() {
        return ZonedDateTime.now();
    }

}
