package com.digyai.notificationservice.controller;

import com.digyai.notificationservice.model.Notification;
import com.digyai.notificationservice.redis.NotificationRedisService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationRedisService redisService;

    public NotificationController(NotificationRedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping
    public List<Notification> getNotifications(HttpSession session) {
        String userId = (String) session.getAttribute("userId"); // fixed: was "USER_ID"
        if (userId == null) throw new RuntimeException("User not logged in");
        return redisService.getNotifications(userId)
                .stream().map(obj -> (Notification) obj).collect(Collectors.toList());
    }

    @PutMapping("/read/{notificationId}")
    public String markAsRead(@PathVariable String notificationId, HttpSession session) {
        String userId = (String) session.getAttribute("userId"); // fixed
        if (userId == null) throw new RuntimeException("User not logged in");
        redisService.markAsRead(userId, notificationId);
        return "Notification marked as read";
    }

    @DeleteMapping("/delete/{notificationId}")
    public String deleteNotification(@PathVariable String notificationId, HttpSession session) {
        String userId = (String) session.getAttribute("userId"); // fixed
        if (userId == null) throw new RuntimeException("User not logged in");
        redisService.deleteNotification(userId, notificationId);
        return "Notification deleted";
    }

    @DeleteMapping("/delete-all")
    public String deleteAllNotifications(HttpSession session) {
        String userId = (String) session.getAttribute("userId"); // fixed
        if (userId == null) throw new RuntimeException("User not logged in");
        redisService.deleteAllNotifications(userId);
        return "All notifications deleted";
    }
}
