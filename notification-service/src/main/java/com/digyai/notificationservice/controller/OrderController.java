package com.digyai.notificationservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * This API simulates an Order Placement.
     * It sends an event to Kafka and immediately returns response.
     */
    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(
            @RequestParam String userId,
            @RequestParam(defaultValue = "Order placed successfully") String message
    ) {

        // ⏱️ Start time (Main Service)
        long startTime = System.currentTimeMillis();

        // Kafka message format used across project
        String kafkaMessage = "ORDER_PLACED|" + userId + "|" + message;

        // 🔥 Send message to Kafka topic (ASYNC)
        kafkaTemplate.send("ecommerce-events", kafkaMessage);

        // ⏱️ End time (Main Service)
        long endTime = System.currentTimeMillis();

        long responseTime = endTime - startTime;

        // ✅ API responds immediately (no waiting for notification)
        return ResponseEntity.ok(
                "Order API response time = " + responseTime + " ms"
        );
    }
}
