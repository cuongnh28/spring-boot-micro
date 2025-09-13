package com.demo.kafka.config;

import com.demo.dto.ProductEvent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ProductKafkaProducer {

    @Autowired
    private KafkaTemplate<String, ProductEvent> kafkaTemplate;

    @Value("${spring.kafka.topic.product.name:product-events}")
    private String productTopic;

    @Value("${spring.kafka.replication.factor:1}")
    private int replicationFactor;

    @Value("${spring.kafka.partition.number:1}")
    private int partitionNumber;

    @Async
    public void sendProductEvent(ProductEvent productEvent) {
        try {
            log.info("Sending product event to Kafka: {}", productEvent);
            kafkaTemplate.send(productTopic, productEvent.getEventType(), productEvent);
            log.info("Product event sent successfully to topic: {}", productTopic);
        } catch (Exception e) {
            log.error("Failed to send product event to Kafka: {}", e.getMessage(), e);
        }
    }

    @Bean
    @Order(-1)
    public NewTopic createProductTopic() {
        return new NewTopic(productTopic, partitionNumber, (short) replicationFactor);
    }
}

