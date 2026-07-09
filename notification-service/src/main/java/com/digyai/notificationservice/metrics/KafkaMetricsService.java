package com.digyai.notificationservice.metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Tracks notification processing metrics in memory.
 * Exposed via /actuator/metrics and the monitoring dashboard.
 */
@Service
public class KafkaMetricsService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaMetricsService.class);

    // Total messages consumed from Kafka
    private final AtomicLong totalMessagesConsumed = new AtomicLong(0);

    // Count per event type
    private final AtomicLong orderPlacedCount   = new AtomicLong(0);
    private final AtomicLong paymentDoneCount   = new AtomicLong(0);
    private final AtomicLong shipmentUpdateCount = new AtomicLong(0);

    // Failed messages sent to DLT
    private final AtomicLong deadLetterCount = new AtomicLong(0);

    // Emails sent
    private final AtomicLong emailsSent = new AtomicLong(0);

    // Total processing time (for average calculation)
    private final AtomicLong totalProcessingTimeMs = new AtomicLong(0);

    // Service start time
    private final long startTimeMs = System.currentTimeMillis();

    public void recordMessage(String eventType, long processingTimeMs) {
        totalMessagesConsumed.incrementAndGet();
        totalProcessingTimeMs.addAndGet(processingTimeMs);

        switch (eventType) {
            case "ORDER_PLACED"    -> orderPlacedCount.incrementAndGet();
            case "PAYMENT_DONE"   -> paymentDoneCount.incrementAndGet();
            case "SHIPMENT_UPDATE" -> shipmentUpdateCount.incrementAndGet();
        }

        logger.debug("[METRICS] Recorded event={} time={}ms total={}", eventType, processingTimeMs, totalMessagesConsumed.get());
    }

    public void recordDeadLetter() {
        deadLetterCount.incrementAndGet();
    }

    public void recordEmailSent() {
        emailsSent.incrementAndGet();
    }

    public long getTotalMessagesConsumed() { return totalMessagesConsumed.get(); }
    public long getOrderPlacedCount()      { return orderPlacedCount.get(); }
    public long getPaymentDoneCount()      { return paymentDoneCount.get(); }
    public long getShipmentUpdateCount()   { return shipmentUpdateCount.get(); }
    public long getDeadLetterCount()       { return deadLetterCount.get(); }
    public long getEmailsSent()            { return emailsSent.get(); }

    public long getUptimeSeconds() {
        return (System.currentTimeMillis() - startTimeMs) / 1000;
    }

    public double getAverageProcessingTimeMs() {
        long total = totalMessagesConsumed.get();
        return total == 0 ? 0.0 : (double) totalProcessingTimeMs.get() / total;
    }
}
