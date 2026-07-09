package com.digyai.notificationservice.model;

public class EventModel {

    private String eventType;
    private String userId;
    private String message;

    public EventModel() {
    }

    public EventModel(String eventType, String userId, String message) {
        this.eventType = eventType;
        this.userId = userId;
        this.message = message;
    }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    @Override
    public String toString() {
        return "EventModel{" +
                "eventType='" + eventType + '\'' +
                ", userId='" + userId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
