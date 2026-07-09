package com.digyai.eventservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.digyai.eventservice.dto.EventRequest;
import com.digyai.eventservice.dto.EventResponse;
import com.digyai.eventservice.kafka.KafkaProducerService;

@Service
public class EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    private final KafkaProducerService kafkaProducerService;

    public EventService(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    public EventResponse publishEvent(EventRequest request) {
        String kafkaMessage = buildKafkaMessage(request);
        logger.info("[EVENT-SERVICE] Publishing | type={} | userId={}", request.getEventType(), request.getUserId());
        kafkaProducerService.sendEvent(request.getUserId(), kafkaMessage);
        return new EventResponse(
                true,
                "Event published to Kafka topic: ecommerce-events",
                request.getEventType(),
                request.getUserId(),
                kafkaMessage,
                System.currentTimeMillis()
        );
    }

    // FORMAT: EVENT_TYPE|userId|message  — matches your NotificationConsumer split logic exactly
    private String buildKafkaMessage(EventRequest req) {
        String orderId = req.getOrderId() != null ? req.getOrderId() : "ORD-" + System.currentTimeMillis();
        String amount  = req.getAmount()  != null ? req.getAmount()  : "0";
        String loc     = req.getLocation() != null ? req.getLocation() : "Warehouse";

        return switch (req.getEventType()) {
            case "ORDER_PLACED" -> String.format(
                    "ORDER_PLACED|%s|Your order %s has been placed! Amount: Rs.%s",
                    req.getUserId(), orderId, amount);
            case "PAYMENT_DONE" -> String.format(
                    "PAYMENT_DONE|%s|Payment of Rs.%s confirmed for order %s. Thank you!",
                    req.getUserId(), amount, orderId);
            case "SHIPMENT_UPDATE" -> String.format(
                    "SHIPMENT_UPDATE|%s|Your order %s is on the way! Current location: %s",
                    req.getUserId(), orderId, loc);
            default -> String.format("%s|%s|%s",
                    req.getEventType(), req.getUserId(),
                    req.getMessage() != null ? req.getMessage() : "Event triggered");
        };
    }
}
