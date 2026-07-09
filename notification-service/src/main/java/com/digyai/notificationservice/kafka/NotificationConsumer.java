package com.digyai.notificationservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.digyai.notificationservice.model.EventModel;
import com.digyai.notificationservice.service.NotificationService;
import com.digyai.notificationservice.metrics.KafkaMetricsService;

@Service
public class NotificationConsumer {

    private static final Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);

    private final NotificationService notificationService;
    private final DeadLetterProducer dltProducer;
    private final KafkaMetricsService metricsService;

    public NotificationConsumer(NotificationService notificationService,
                                DeadLetterProducer dltProducer,
                                KafkaMetricsService metricsService) {
        this.notificationService = notificationService;
        this.dltProducer         = dltProducer;
        this.metricsService      = metricsService;
    }

    @KafkaListener(topics = "ecommerce-events", groupId = "notification-group-v2")
    public void consume(String message) {

        long startTime = System.currentTimeMillis();

        try {
            logger.info("📥 Received message from Kafka: {}", message);

            String[] parts = message.split("\\|");

            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid message format");
            }

            EventModel event = new EventModel();
            event.setEventType(parts[0]);
            event.setUserId(parts[1]);
            event.setMessage(parts[2]);

            notificationService.sendNotification(event);

            long processingTime = System.currentTimeMillis() - startTime;

            // Record metrics
            metricsService.recordMessage(event.getEventType(), processingTime);

            logger.info("✅ Notification processed for user: {} | Processing time: {} ms",
                    event.getUserId(), processingTime);

        } catch (Exception e) {
            logger.error("❌ Error processing message, sending to DLT: {}", message, e);
            metricsService.recordDeadLetter();
            dltProducer.sendToDLT(message);
        }
    }
}
