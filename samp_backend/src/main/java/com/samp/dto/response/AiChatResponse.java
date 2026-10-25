package com.samp.dto.response;

import java.time.LocalDateTime;

public class AiChatResponse {
    private String answer;
    private String modelUsed;
    private LocalDateTime timestamp;

    public AiChatResponse() {}

    public AiChatResponse(String answer, String modelUsed, LocalDateTime timestamp) {
        this.answer = answer;
        this.modelUsed = modelUsed;
        this.timestamp = timestamp;
    }

    public static AiChatResponseBuilder builder() {
        return new AiChatResponseBuilder();
    }

    public static class AiChatResponseBuilder {
        private String answer;
        private String modelUsed;
        private LocalDateTime timestamp;

        public AiChatResponseBuilder answer(String answer) { this.answer = answer; return this; }
        public AiChatResponseBuilder modelUsed(String modelUsed) { this.modelUsed = modelUsed; return this; }
        public AiChatResponseBuilder timestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }

        public AiChatResponse build() {
            return new AiChatResponse(answer, modelUsed, timestamp);
        }
    }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public String getModelUsed() { return modelUsed; }
    public void setModelUsed(String modelUsed) { this.modelUsed = modelUsed; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
