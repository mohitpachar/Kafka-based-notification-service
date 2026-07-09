package com.digyai.notificationservice.websocket;
 
import com.digyai.notificationservice.model.Notification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
 
@Service
public class WebSocketNotificationService {
 
    private final SimpMessagingTemplate messagingTemplate;
 
    public WebSocketNotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
 
    public void sendNotification(String userId, Notification notification) {
        // Send to /topic/notifications/{userId}
        // Frontend subscribes to this exact topic
        messagingTemplate.convertAndSend(
                "/topic/notifications/" + userId,
                notification
        );
    }
}