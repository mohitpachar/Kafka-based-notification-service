package com.digyai.notificationservice.controller;

import org.springframework.web.bind.annotation.*;

import com.digyai.notificationservice.kafka.NotificationProducer;
import com.digyai.notificationservice.model.EventModel;

@RestController
@RequestMapping("/producer")
public class ProducerController {

    private final NotificationProducer producer;

    public ProducerController(NotificationProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/send")
    public String sendNotification(@RequestParam String eventType,
                                   @RequestParam String userId,
                                   @RequestParam String message) {

        String kafkaMessage = eventType + "|" + userId + "|" + message;

        producer.send(kafkaMessage);

        return "Notification sent to Kafka";
    }

}
