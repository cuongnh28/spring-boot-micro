package com.demo.interceptor;

import com.demo.constants.CorrelationConstants;
import com.demo.util.CorrelationUtils;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArguments;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.MDC;
import org.springframework.kafka.listener.RecordInterceptor;

import java.util.Map;

@Slf4j
public class KafkaRecordInterceptor<K, V>  implements RecordInterceptor<K, V> {

    @Override
    public ConsumerRecord<K, V> intercept(ConsumerRecord<K, V> consumerRecord, Consumer<K, V> consumer) {
        MDC.put(CorrelationConstants.CONTEXT_CORRELATION_ID.getValue(), CorrelationUtils.generateCorrelationId());
        MDC.put("topic", consumerRecord.topic());
        MDC.put("partition", String.valueOf(consumerRecord.partition()));
        MDC.put("offset", String.valueOf(consumerRecord.offset()));
        MDC.put("key", String.valueOf(consumerRecord.key()));
        log.info("payload",StructuredArguments.entries(Map.of("payload", consumerRecord.value())));
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

