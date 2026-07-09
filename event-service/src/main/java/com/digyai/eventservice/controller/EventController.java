package com.digyai.eventservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digyai.eventservice.dto.EventRequest;
import com.digyai.eventservice.dto.EventResponse;
import com.digyai.eventservice.service.EventService;

import java.util.Map;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /** POST /api/events/publish */
    @PostMapping("/publish")
    public ResponseEntity<EventResponse> publishEvent(@RequestBody EventRequest request) {
        return ResponseEntity.ok(eventService.publishEvent(request));
    }

    /** POST /api/events/order  { "userId":"user1", "orderId":"ORD123", "amount":"999" } */
    @PostMapping("/order")
    public ResponseEntity<EventResponse> placeOrder(@RequestBody EventRequest request) {
        request.setEventType("ORDER_PLACED");
        return ResponseEntity.ok(eventService.publishEvent(request));
    }

    /** POST /api/events/payment  { "userId":"user1", "orderId":"ORD123", "amount":"999" } */
    @PostMapping("/payment")
    public ResponseEntity<EventResponse> processPayment(@RequestBody EventRequest request) {
        request.setEventType("PAYMENT_DONE");
        return ResponseEntity.ok(eventService.publishEvent(request));
    }

    /** POST /api/events/shipment  { "userId":"user1", "orderId":"ORD123", "location":"Mumbai Hub" } */
    @PostMapping("/shipment")
    public ResponseEntity<EventResponse> shipmentUpdate(@RequestBody EventRequest request) {
        request.setEventType("SHIPMENT_UPDATE");
        return ResponseEntity.ok(eventService.publishEvent(request));
    }

    /** GET /api/events/health */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "event-service",
                "port", "8082",
                "kafkaTopic", "ecommerce-events"
        ));
    }
}
