package com.digyai.eventservice.dto;

public class EventResponse {

    private boolean success;
    private String message;
    private String eventType;
    private String userId;
    private String kafkaMessage;
    private long timestamp;

    public EventResponse() {}

    public EventResponse(boolean success, String message, String eventType,
                         String userId, String kafkaMessage, long timestamp) {
        this.success = success;
        this.message = message;
        this.eventType = eventType;
        this.userId = userId;
        this.kafkaMessage = kafkaMessage;
        this.timestamp = timestamp;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getKafkaMessage() { return kafkaMessage; }
    public void setKafkaMessage(String kafkaMessage) { this.kafkaMessage = kafkaMessage; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
