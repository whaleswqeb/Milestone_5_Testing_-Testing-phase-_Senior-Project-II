package com.samp.entity;

import com.samp.enums.QueryCategory;
import com.samp.enums.QueryPriority;
import com.samp.enums.QueryStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student_queries")
public class StudentQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_user_id")
    private User assignedTo;

    @Column(nullable = false)
    private String subject;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QueryCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QueryPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QueryStatus status;

    @OneToMany(mappedBy = "query", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QueryMessage> messages = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public StudentQuery() {}

    public StudentQuery(Long id, User student, User assignedTo, String subject, QueryCategory category, QueryPriority priority, QueryStatus status, List<QueryMessage> messages, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.student = student;
        this.assignedTo = assignedTo;
        this.subject = subject;
        this.category = category;
        this.priority = priority;
        this.status = status;
        this.messages = messages != null ? messages : new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static StudentQueryBuilder builder() {
        return new StudentQueryBuilder();
    }

    public static class StudentQueryBuilder {
        private Long id;
        private User student;
        private User assignedTo;
        private String subject;
        private QueryCategory category;
        private QueryPriority priority;
        private QueryStatus status;
        private List<QueryMessage> messages = new ArrayList<>();
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public StudentQueryBuilder id(Long id) { this.id = id; return this; }
        public StudentQueryBuilder student(User student) { this.student = student; return this; }
        public StudentQueryBuilder assignedTo(User assignedTo) { this.assignedTo = assignedTo; return this; }
        public StudentQueryBuilder subject(String subject) { this.subject = subject; return this; }
        public StudentQueryBuilder category(QueryCategory category) { this.category = category; return this; }
        public StudentQueryBuilder priority(QueryPriority priority) { this.priority = priority; return this; }
        public StudentQueryBuilder status(QueryStatus status) { this.status = status; return this; }
        public StudentQueryBuilder messages(List<QueryMessage> messages) { this.messages = messages; return this; }
        public StudentQueryBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public StudentQueryBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public StudentQuery build() {
            return new StudentQuery(id, student, assignedTo, subject, category, priority, status, messages, createdAt, updatedAt);
        }
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = QueryStatus.OPEN;
        if (priority == null) priority = QueryPriority.MEDIUM;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }

    public User getAssignedTo() { return assignedTo; }
    public void setAssignedTo(User assignedTo) { this.assignedTo = assignedTo; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public QueryCategory getCategory() { return category; }
    public void setCategory(QueryCategory category) { this.category = category; }

    public QueryPriority getPriority() { return priority; }
    public void setPriority(QueryPriority priority) { this.priority = priority; }

    public QueryStatus getStatus() { return status; }
    public void setStatus(QueryStatus status) { this.status = status; }

    public List<QueryMessage> getMessages() { return messages; }
    public void setMessages(List<QueryMessage> messages) { this.messages = messages; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
