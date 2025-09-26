package com.demo.kafka.config;

import com.demo.constants.CorrelationConstants;
import com.demo.dto.ProductEvent;
import com.demo.util.CorrelationUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Vito Nguyen (<a href="https://github.com/cuongnh28">...</a>)
 */


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

    @Async("asyncTaskExecutor")
    public void sendProductEvent(ProductEvent productEvent) {
        try {
            log.info("Sending product event to Kafka: {}", productEvent);
            String correlationId = CorrelationUtils.currentCorrelationId();
            log.info("Producer MDC correlation_id={}", correlationId);
            if (correlationId == null || correlationId.isBlank()) {
                correlationId = CorrelationUtils.generateCorrelationId();
            }
            ProducerRecord<String, ProductEvent> record = new ProducerRecord<>(productTopic, productEvent.getEventType() == null ? null : productEvent.getEventType().name(), productEvent);
            record.headers().add(CorrelationConstants.CONTEXT_CORRELATION_ID.getValue(), correlationId.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            kafkaTemplate.send(record);
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

