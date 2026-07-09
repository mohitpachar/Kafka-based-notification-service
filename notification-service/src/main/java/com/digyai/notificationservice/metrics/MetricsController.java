package com.digyai.notificationservice.metrics;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/metrics")
@CrossOrigin(originPatterns = "*")
public class MetricsController {

    private final KafkaMetricsService metricsService;

    public MetricsController(KafkaMetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping("/summary")
    public Map<String, Object> getSummary() {
        Map<String, Object> result = new HashMap<>();
        result.put("service",               "notification-service");
        result.put("port",                  8081);
        result.put("status",                "UP");
        result.put("uptimeSeconds",         metricsService.getUptimeSeconds());
        result.put("totalMessagesConsumed", metricsService.getTotalMessagesConsumed());
        result.put("orderPlaced",           metricsService.getOrderPlacedCount());
        result.put("paymentDone",           metricsService.getPaymentDoneCount());
        result.put("shipmentUpdate",        metricsService.getShipmentUpdateCount());
        result.put("deadLetterCount",       metricsService.getDeadLetterCount());
        result.put("emailsSent",            metricsService.getEmailsSent());
        result.put("avgProcessingTimeMs",   Math.round(metricsService.getAverageProcessingTimeMs()));
        return result;
    }
}