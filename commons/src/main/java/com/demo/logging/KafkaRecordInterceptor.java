package com.demo.logging;

import com.demo.constants.CorrelationConstants;
import com.demo.context.CommonContextHolder;
import com.demo.util.CorrelationUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.slf4j.MDC;
import org.springframework.kafka.listener.RecordInterceptor;

import java.nio.charset.StandardCharsets;

/**
 * @author Vito Nguyen (<a href="https://github.com/cuongnh28">...</a>)
 */


@Slf4j
public class KafkaRecordInterceptor<K, V>  implements RecordInterceptor<K, V> {

    public KafkaRecordInterceptor() {
        log.info("KafkaRecordInterceptor instantiated - ready to intercept Kafka messages");
    }

    @Override
    public ConsumerRecord<K, V> intercept(ConsumerRecord<K, V> consumerRecord, Consumer<K, V> consumer) {
        String correlationId = null;
        Header header = consumerRecord.headers().lastHeader(CorrelationConstants.CONTEXT_CORRELATION_ID.getValue());
        if (header != null && header.value() != null) {
            correlationId = new String(header.value(), StandardCharsets.UTF_8);
        }
        if (correlationId == null || correlationId.isBlank()) {
            correlationId = CommonContextHolder.getCorrelationId();
        }
        if (correlationId == null || correlationId.isBlank()) {
            correlationId = CorrelationUtils.generateCorrelationId();
        }
        MDC.put(CorrelationConstants.CONTEXT_CORRELATION_ID.getValue(), correlationId);
        MDC.put("topic", consumerRecord.topic());
        MDC.put("partition", String.valueOf(consumerRecord.partition()));
        MDC.put("offset", String.valueOf(consumerRecord.offset()));
        MDC.put("key", String.valueOf(consumerRecord.key()));
        MDC.put("event", "kafka_message_intercepted");
        // Grep-friendly info with payload captured in MDC (keep original content)
        String payloadStr = String.valueOf(consumerRecord.value());
        MDC.put("payload", payloadStr);
        log.info("Kafka message intercepted: topic={}, partition={}, offset={}, key={}, payload={}",
                consumerRecord.topic(), consumerRecord.partition(), consumerRecord.offset(),
                consumerRecord.key(), payloadStr);
        return consumerRecord;
    }

    @Override
    public void success(ConsumerRecord<K, V> record, Consumer<K, V> consumer) {
        RecordInterceptor.super.success(record, consumer);
    }

    @Override
    public void failure(ConsumerRecord<K, V> record, Exception exception, Consumer<K, V> consumer) {
        RecordInterceptor.super.failure(record, exception, consumer);
    }

    @Override
    public void afterRecord(ConsumerRecord<K, V> record, Consumer<K, V> consumer) {
        RecordInterceptor.super.afterRecord(record, consumer);
    }

    @Override
    public void setupThreadState(Consumer<?, ?> consumer) {
        RecordInterceptor.super.setupThreadState(consumer);
    }

    @Override
    public void clearThreadState(Consumer<?, ?> consumer) {
        RecordInterceptor.super.clearThreadState(consumer);
    }
}

