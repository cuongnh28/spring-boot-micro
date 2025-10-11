package com.demo.controller;

import com.demo.constants.CorrelationConstants;
import com.demo.dto.User;
import com.demo.kafka.config.UserKafkaProducer;
import com.demo.util.CorrelationUtils;
import com.github.javafaker.Faker;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import java.time.ZonedDateTime;

/**
 * @author Vito Nguyen (<a href="https://github.com/cuongnh28">...</a>)
 */


/**
 * Kafka Testing Controller - sends test messages to trigger KafkaRecordInterceptor
 */
@RestController
@RequestMapping("api/user")
@Tag(name = "user-controller", description = "Utility endpoint to generate random users and send to Kafka")
public class UserController {

    @Autowired
    UserKafkaProducer userKafkaProducer;

    /**
     * Send a random user to Kafka for testing
     */
    @PostMapping()
    @Operation(summary = "Generate and send user to Kafka", description = "Creates a random user and publishes to Kafka for testing")
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

