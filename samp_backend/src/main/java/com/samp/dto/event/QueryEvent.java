package com.samp.dto.event;

import com.samp.enums.QueryCategory;
import com.samp.enums.QueryPriority;
import com.samp.enums.QueryStatus;
import java.time.LocalDateTime;

public class QueryEvent {
    private String eventType;
    private Long queryId;
    private Long studentId;
    private String studentName;
    private String studentEmail;
    private Long senderId;
    private String senderEmail;
    private Long targetUserId;
    private String subject;
    private QueryCategory category;
    private QueryPriority priority;
    private QueryStatus status;
    private String latestMessage;
    private LocalDateTime timestamp;

    public QueryEvent() {}

    public QueryEvent(String eventType, Long queryId, Long studentId, String studentName, String studentEmail, Long senderId, String senderEmail, Long targetUserId, String subject, QueryCategory category, QueryPriority priority, QueryStatus status, String latestMessage, LocalDateTime timestamp) {
        this.eventType = eventType;
        this.queryId = queryId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.senderId = senderId;
        this.senderEmail = senderEmail;
        this.targetUserId = targetUserId;
        this.subject = subject;
        this.category = category;
        this.priority = priority;
        this.status = status;
        this.latestMessage = latestMessage;
        this.timestamp = timestamp;
    }

    public static QueryEventBuilder builder() {
        return new QueryEventBuilder();
    }

    public static class QueryEventBuilder {
        private String eventType;
        private Long queryId;
        private Long studentId;
        private String studentName;
        private String studentEmail;
        private Long senderId;
        private String senderEmail;
        private Long targetUserId;
        private String subject;
        private QueryCategory category;
        private QueryPriority priority;
        private QueryStatus status;
        private String latestMessage;
        private LocalDateTime timestamp;

        public QueryEventBuilder eventType(String eventType) { this.eventType = eventType; return this; }
        public QueryEventBuilder queryId(Long queryId) { this.queryId = queryId; return this; }
        public QueryEventBuilder studentId(Long studentId) { this.studentId = studentId; return this; }
        public QueryEventBuilder studentName(String studentName) { this.studentName = studentName; return this; }
        public QueryEventBuilder studentEmail(String studentEmail) { this.studentEmail = studentEmail; return this; }
        public QueryEventBuilder senderId(Long senderId) { this.senderId = senderId; return this; }
        public QueryEventBuilder senderEmail(String senderEmail) { this.senderEmail = senderEmail; return this; }
        public QueryEventBuilder targetUserId(Long targetUserId) { this.targetUserId = targetUserId; return this; }
        public QueryEventBuilder subject(String subject) { this.subject = subject; return this; }
        public QueryEventBuilder category(QueryCategory category) { this.category = category; return this; }
        public QueryEventBuilder priority(QueryPriority priority) { this.priority = priority; return this; }
        public QueryEventBuilder status(QueryStatus status) { this.status = status; return this; }
        public QueryEventBuilder latestMessage(String latestMessage) { this.latestMessage = latestMessage; return this; }
        public QueryEventBuilder timestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }

        public QueryEvent build() {
            return new QueryEvent(eventType, queryId, studentId, studentName, studentEmail, senderId, senderEmail, targetUserId, subject, category, priority, status, latestMessage, timestamp);
        }
    }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public Long getQueryId() { return queryId; }
    public void setQueryId(Long queryId) { this.queryId = queryId; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentEmail() { return studentEmail; }
    public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }

    public Long getSenderId() { return senderId; }
    public void setSenderId(Long senderId) { this.senderId = senderId; }

    public String getSenderEmail() { return senderEmail; }
    public void setSenderEmail(String senderEmail) { this.senderEmail = senderEmail; }

    public Long getTargetUserId() { return targetUserId; }
    public void setTargetUserId(Long targetUserId) { this.targetUserId = targetUserId; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public QueryCategory getCategory() { return category; }
    public void setCategory(QueryCategory category) { this.category = category; }

    public QueryPriority getPriority() { return priority; }
    public void setPriority(QueryPriority priority) { this.priority = priority; }

    public QueryStatus getStatus() { return status; }
    public void setStatus(QueryStatus status) { this.status = status; }

    public String getLatestMessage() { return latestMessage; }
    public void setLatestMessage(String latestMessage) { this.latestMessage = latestMessage; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
