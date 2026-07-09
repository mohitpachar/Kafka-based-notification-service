package com.digyai.notificationservice.redis;

import com.digyai.notificationservice.model.Notification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationRedisService {

    private static final String KEY_PREFIX = "NOTIFICATION_";

    private final RedisTemplate<String, Notification> redisTemplate;

    public NotificationRedisService(RedisTemplate<String, Notification> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 1️⃣ Save notification
    public void saveNotification(String userId, Notification notification) {
        redisTemplate.opsForList()
                .rightPush(KEY_PREFIX + userId, notification);
    }

    // 2️⃣ Get all notifications
    public List<Notification> getNotifications(String userId) {
        return redisTemplate.opsForList()
                .range(KEY_PREFIX + userId, 0, -1);
    }

    // 3️⃣ Mark notification as READ
    public void markAsRead(String userId, String notificationId) {

        String key = KEY_PREFIX + userId;
        List<Notification> notifications = getNotifications(userId);

        if (notifications == null || notifications.isEmpty()) return;

        // Clear list
        redisTemplate.delete(key);

        // Reinsert updated notifications
        for (Notification n : notifications) {
            if (n.getId().equals(notificationId)) {
                n.setRead(true);
            }
            redisTemplate.opsForList().rightPush(key, n);
        }
    }

    // 4️⃣ Delete notification
    public void deleteNotification(String userId, String notificationId) {

        String key = KEY_PREFIX + userId;
        List<Notification> notifications = getNotifications(userId);

        if (notifications == null || notifications.isEmpty()) return;

        for (Notification n : notifications) {
            if (n.getId().equals(notificationId)) {
                redisTemplate.opsForList().remove(key, 1, n);
                break;
            }
        }
    }
    
 // 5️⃣ Delete ALL notifications ✅ FIXED
    public void deleteAllNotifications(String userId) {
        redisTemplate.delete(KEY_PREFIX + userId);
    }

}
