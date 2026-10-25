package com.samp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "query_messages")
public class QueryMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "query_id", nullable = false)
    private StudentQuery query;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    public QueryMessage() {}

    public QueryMessage(Long id, StudentQuery query, User sender, String message, LocalDateTime createdAt) {
        this.id = id;
        this.query = query;
        this.sender = sender;
        this.message = message;
        this.createdAt = createdAt;
    }

    public static QueryMessageBuilder builder() {
        return new QueryMessageBuilder();
    }

    public static class QueryMessageBuilder {
        private Long id;
        private StudentQuery query;
        private User sender;
        private String message;
        private LocalDateTime createdAt;

        public QueryMessageBuilder id(Long id) { this.id = id; return this; }
        public QueryMessageBuilder query(StudentQuery query) { this.query = query; return this; }
        public QueryMessageBuilder sender(User sender) { this.sender = sender; return this; }
        public QueryMessageBuilder message(String message) { this.message = message; return this; }
        public QueryMessageBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public QueryMessage build() {
            return new QueryMessage(id, query, sender, message, createdAt);
        }
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public StudentQuery getQuery() { return query; }
    public void setQuery(StudentQuery query) { this.query = query; }

    public User getSender() { return sender; }
    public void setSender(User sender) { this.sender = sender; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
