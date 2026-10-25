package com.samp.dto.response;

import com.samp.enums.NotificationType;
import java.time.LocalDateTime;

public class NotificationResponse {

    private Long id;
    private String message;
    private NotificationType type;
    private boolean read;
    private LocalDateTime createdAt;

    public NotificationResponse() {}

    public NotificationResponse(Long id, String message, NotificationType type, boolean read, LocalDateTime createdAt) {
        this.id = id;
        this.message = message;
        this.type = type;
        this.read = read;
        this.createdAt = createdAt;
    }

    public static NotificationResponseBuilder builder() {
        return new NotificationResponseBuilder();
    }

    public static class NotificationResponseBuilder {
        private Long id;
        private String message;
        private NotificationType type;
        private boolean read;
        private LocalDateTime createdAt;

        public NotificationResponseBuilder id(Long id) { this.id = id; return this; }
        public NotificationResponseBuilder message(String message) { this.message = message; return this; }
        public NotificationResponseBuilder type(NotificationType type) { this.type = type; return this; }
        public NotificationResponseBuilder read(boolean read) { this.read = read; return this; }
        public NotificationResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public NotificationResponse build() {
            return new NotificationResponse(id, message, type, read, createdAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public NotificationType getType() { return type; }
    public void setType(NotificationType type) { this.type = type; }

    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}