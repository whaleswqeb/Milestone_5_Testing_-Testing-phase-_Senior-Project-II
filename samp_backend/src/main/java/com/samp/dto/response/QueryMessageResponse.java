package com.samp.dto.response;

import java.time.LocalDateTime;

public class QueryMessageResponse {
    private Long id;
    private Long queryId;
    private Long senderId;
    private String senderName;
    private String senderRole;
    private String message;
    private LocalDateTime createdAt;

    public QueryMessageResponse() {}

    public QueryMessageResponse(Long id, Long queryId, Long senderId, String senderName, String senderRole, String message, LocalDateTime createdAt) {
        this.id = id;
        this.queryId = queryId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderRole = senderRole;
        this.message = message;
        this.createdAt = createdAt;
    }

    public static QueryMessageResponseBuilder builder() {
        return new QueryMessageResponseBuilder();
    }

    public static class QueryMessageResponseBuilder {
        private Long id;
        private Long queryId;
        private Long senderId;
        private String senderName;
        private String senderRole;
        private String message;
        private LocalDateTime createdAt;

        public QueryMessageResponseBuilder id(Long id) { this.id = id; return this; }
        public QueryMessageResponseBuilder queryId(Long queryId) { this.queryId = queryId; return this; }
        public QueryMessageResponseBuilder senderId(Long senderId) { this.senderId = senderId; return this; }
        public QueryMessageResponseBuilder senderName(String senderName) { this.senderName = senderName; return this; }
        public QueryMessageResponseBuilder senderRole(String senderRole) { this.senderRole = senderRole; return this; }
        public QueryMessageResponseBuilder message(String message) { this.message = message; return this; }
        public QueryMessageResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public QueryMessageResponse build() {
            return new QueryMessageResponse(id, queryId, senderId, senderName, senderRole, message, createdAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getQueryId() { return queryId; }
    public void setQueryId(Long queryId) { this.queryId = queryId; }

    public Long getSenderId() { return senderId; }
    public void setSenderId(Long senderId) { this.senderId = senderId; }

    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }

    public String getSenderRole() { return senderRole; }
    public void setSenderRole(String senderRole) { this.senderRole = senderRole; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
