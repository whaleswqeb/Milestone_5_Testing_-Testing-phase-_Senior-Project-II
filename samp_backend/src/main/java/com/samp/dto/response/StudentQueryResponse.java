package com.samp.dto.response;

import com.samp.enums.QueryCategory;
import com.samp.enums.QueryPriority;
import com.samp.enums.QueryStatus;

import java.time.LocalDateTime;
import java.util.List;

public class StudentQueryResponse {
    private Long id;
    private Long studentId;
    private String studentName;
    private String studentEmail;
    private Long assignedToId;
    private String assignedToName;
    private String subject;
    private QueryCategory category;
    private QueryPriority priority;
    private QueryStatus status;
    private List<QueryMessageResponse> messages;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public StudentQueryResponse() {}

    public StudentQueryResponse(Long id, Long studentId, String studentName, String studentEmail, Long assignedToId, String assignedToName, String subject, QueryCategory category, QueryPriority priority, QueryStatus status, List<QueryMessageResponse> messages, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.assignedToId = assignedToId;
        this.assignedToName = assignedToName;
        this.subject = subject;
        this.category = category;
        this.priority = priority;
        this.status = status;
        this.messages = messages;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static StudentQueryResponseBuilder builder() {
        return new StudentQueryResponseBuilder();
    }

    public static class StudentQueryResponseBuilder {
        private Long id;
        private Long studentId;
        private String studentName;
        private String studentEmail;
        private Long assignedToId;
        private String assignedToName;
        private String subject;
        private QueryCategory category;
        private QueryPriority priority;
        private QueryStatus status;
        private List<QueryMessageResponse> messages;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public StudentQueryResponseBuilder id(Long id) { this.id = id; return this; }
        public StudentQueryResponseBuilder studentId(Long studentId) { this.studentId = studentId; return this; }
        public StudentQueryResponseBuilder studentName(String studentName) { this.studentName = studentName; return this; }
        public StudentQueryResponseBuilder studentEmail(String studentEmail) { this.studentEmail = studentEmail; return this; }
        public StudentQueryResponseBuilder assignedToId(Long assignedToId) { this.assignedToId = assignedToId; return this; }
        public StudentQueryResponseBuilder assignedToName(String assignedToName) { this.assignedToName = assignedToName; return this; }
        public StudentQueryResponseBuilder subject(String subject) { this.subject = subject; return this; }
        public StudentQueryResponseBuilder category(QueryCategory category) { this.category = category; return this; }
        public StudentQueryResponseBuilder priority(QueryPriority priority) { this.priority = priority; return this; }
        public StudentQueryResponseBuilder status(QueryStatus status) { this.status = status; return this; }
        public StudentQueryResponseBuilder messages(List<QueryMessageResponse> messages) { this.messages = messages; return this; }
        public StudentQueryResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public StudentQueryResponseBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public StudentQueryResponse build() {
            return new StudentQueryResponse(id, studentId, studentName, studentEmail, assignedToId, assignedToName, subject, category, priority, status, messages, createdAt, updatedAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentEmail() { return studentEmail; }
    public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }

    public Long getAssignedToId() { return assignedToId; }
    public void setAssignedToId(Long assignedToId) { this.assignedToId = assignedToId; }

    public String getAssignedToName() { return assignedToName; }
    public void setAssignedToName(String assignedToName) { this.assignedToName = assignedToName; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public QueryCategory getCategory() { return category; }
    public void setCategory(QueryCategory category) { this.category = category; }

    public QueryPriority getPriority() { return priority; }
    public void setPriority(QueryPriority priority) { this.priority = priority; }

    public QueryStatus getStatus() { return status; }
    public void setStatus(QueryStatus status) { this.status = status; }

    public List<QueryMessageResponse> getMessages() { return messages; }
    public void setMessages(List<QueryMessageResponse> messages) { this.messages = messages; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
