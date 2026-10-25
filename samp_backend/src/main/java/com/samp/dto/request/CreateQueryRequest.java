package com.samp.dto.request;

import com.samp.enums.QueryCategory;
import com.samp.enums.QueryPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateQueryRequest {

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Initial message body is required")
    private String initialMessage;

    @NotNull(message = "Category is required")
    private QueryCategory category;

    private QueryPriority priority;

    private Long assignedFacultyId;

    public CreateQueryRequest() {}

    public CreateQueryRequest(String subject, String initialMessage, QueryCategory category, QueryPriority priority, Long assignedFacultyId) {
        this.subject = subject;
        this.initialMessage = initialMessage;
        this.category = category;
        this.priority = priority;
        this.assignedFacultyId = assignedFacultyId;
    }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getInitialMessage() { return initialMessage; }
    public void setInitialMessage(String initialMessage) { this.initialMessage = initialMessage; }

    public QueryCategory getCategory() { return category; }
    public void setCategory(QueryCategory category) { this.category = category; }

    public QueryPriority getPriority() { return priority; }
    public void setPriority(QueryPriority priority) { this.priority = priority; }

    public Long getAssignedFacultyId() { return assignedFacultyId; }
    public void setAssignedFacultyId(Long assignedFacultyId) { this.assignedFacultyId = assignedFacultyId; }
}
