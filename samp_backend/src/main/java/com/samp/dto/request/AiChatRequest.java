package com.samp.dto.request;

import jakarta.validation.constraints.NotBlank;

public class AiChatRequest {

    @NotBlank(message = "Message prompt is required")
    private String prompt;

    private String context;

    public AiChatRequest() {}

    public AiChatRequest(String prompt, String context) {
        this.prompt = prompt;
        this.context = context;
    }

    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }

    public String getContext() { return context; }
    public void setContext(String context) { this.context = context; }
}
