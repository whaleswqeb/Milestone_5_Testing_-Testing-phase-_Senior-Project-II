package com.samp.dto.request;

import jakarta.validation.constraints.NotBlank;

public class QueryMessageRequest {

    @NotBlank(message = "Message text is required")
    private String message;

    public QueryMessageRequest() {}

    public QueryMessageRequest(String message) {
        this.message = message;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
