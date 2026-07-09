package com.digyai.notificationservice.service;

import com.digyai.notificationservice.email.EmailService;
import com.digyai.notificationservice.metrics.KafkaMetricsService;
import com.digyai.notificationservice.model.EventModel;
import com.digyai.notificationservice.model.Notification;
import com.digyai.notificationservice.redis.NotificationRedisService;
import com.digyai.notificationservice.websocket.WebSocketNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRedisService redisService;
    private final WebSocketNotificationService socketService;
    private final EmailService emailService;
    private final UserService userService;
    private final KafkaMetricsService metricsService;

    public NotificationService(NotificationRedisService redisService,
                               WebSocketNotificationService socketService,
                               EmailService emailService,
                               UserService userService,
                               KafkaMetricsService metricsService) {
        this.redisService   = redisService;
        this.socketService  = socketService;
        this.emailService   = emailService;
        this.userService    = userService;
        this.metricsService = metricsService;
    }

    public void sendNotification(EventModel event) {

        Notification notification = new Notification(
                event.getEventType(),
                event.getUserId(),
                event.getMessage()
        );

        // 1. Store in Redis
        redisService.saveNotification(event.getUserId(), notification);

        // 2. Push live via WebSocket
        socketService.sendNotification(event.getUserId(), notification);

        System.out.println("Notification saved and pushed for user: " + event.getUserId());

        // 3. Send email if registered
        String userEmail = userService.getUserEmail(event.getUserId());
        if (userEmail != null && !userEmail.isBlank()) {
            emailService.sendNotificationEmail(
                    userEmail,
                    event.getUserId(),
                    event.getEventType(),
                    event.getMessage()
            );
            metricsService.recordEmailSent();
        } else {
            logger.info("[NOTIFICATION-SERVICE] No email registered for userId={}, skipping email",
                    event.getUserId());
        }
    }
}
