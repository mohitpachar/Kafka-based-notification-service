package com.digyai.notificationservice.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String type;
    private String userId;
    private String message;
    private boolean read;
    private LocalDateTime timestamp;

    // ✅ Constructor used by Kafka → Service → Redis
    public Notification(String type, String userId, String message) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.userId = userId;
        this.message = message;
        this.read = false;
        this.timestamp = LocalDateTime.now();
    }

    // ✅ Empty constructor (important for Redis / Jackson)
    public Notification() {
    }

    // -------- Getters & Setters --------

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
