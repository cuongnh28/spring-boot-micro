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

/**
 * Kafka Testing Controller - sends test messages to trigger KafkaRecordInterceptor
 */
@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserKafkaProducer userKafkaProducer;

    /**
     * Send a random user to Kafka for testing
     */
    @PostMapping()
    public boolean generateUser() {
        Faker faker = new Faker();
        User user = new User();
        user.setUsername(faker.name().username());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());
        user.setEnabled(true);
        userKafkaProducer.writeToKafka(user);
        return true;
    }



}
