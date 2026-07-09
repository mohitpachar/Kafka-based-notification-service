package com.digyai.notificationservice.model;

import java.io.Serializable;

public class UserProfile implements Serializable {

    private String userId;
    private String email;
    private long registeredAt;

    public UserProfile() {}

    public UserProfile(String userId, String email) {
        this.userId = userId;
        this.email = email;
        this.registeredAt = System.currentTimeMillis();
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public long getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(long registeredAt) { this.registeredAt = registeredAt; }
}
