package com.digyai.eventservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topic;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(String userId, String message) {
        logger.info("[EVENT-SERVICE] Sending to topic={} | key={} | message={}", topic, userId, message);

        CompletableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(topic, userId, message);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                logger.error("[EVENT-SERVICE] FAILED to send | userId={} | error={}", userId, ex.getMessage());
            } else {
                logger.info("[EVENT-SERVICE] SUCCESS | userId={} | partition={} | offset={}",
                        userId,
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }
        });
    }
}
