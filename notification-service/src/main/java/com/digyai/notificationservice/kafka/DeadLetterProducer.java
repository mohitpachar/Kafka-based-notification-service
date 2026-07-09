package com.digyai.notificationservice.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DeadLetterProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public DeadLetterProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendToDLT(String message) {
        kafkaTemplate.send("ecommerce-events-dlt", message);
    }
}
