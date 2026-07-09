package com.digyai.notificationservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final String KEY_PREFIX = "USER_EMAIL_";

    // StringRedisTemplate is ALWAYS available in Spring Boot — no custom config needed
    private final StringRedisTemplate redisTemplate;

    public UserService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Save userId -> email mapping to Redis.
     * Key: USER_EMAIL_user101  Value: mohit@gmail.com
     */
    public void saveUserProfile(String userId, String email) {
        try {
            redisTemplate.opsForValue().set(KEY_PREFIX + userId, email);
            logger.info("[USER-SERVICE] Saved email for userId={}", userId);
        } catch (Exception e) {
            logger.error("[USER-SERVICE] Failed to save email for userId={}", userId, e);
        }
    }

    /**
     * Get email for userId from Redis.
     * Returns null if not found.
     */
    public String getUserEmail(String userId) {
        try {
            return redisTemplate.opsForValue().get(KEY_PREFIX + userId);
        } catch (Exception e) {
            logger.warn("[USER-SERVICE] Could not get email for userId={}", userId);
            return null;
        }
    }
}
