package com.samp.controller;

import com.samp.dto.request.AiChatRequest;
import com.samp.dto.response.AiChatResponse;
import com.samp.dto.response.ApiResponse;
import com.samp.entity.User;
import com.samp.service.impl.AiChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
@Tag(name = "Spring AI Assistant", description = "AI Academic Assistant Endpoint")
public class AiChatController {

    private final AiChatService aiChatService;

    public AiChatController(AiChatService aiChatService) {
        this.aiChatService = aiChatService;
    }

    @PostMapping("/chat")
    @Operation(summary = "Chat with Spring AI Academic Assistant")
    public ResponseEntity<ApiResponse<AiChatResponse>> chat(
            @Valid @RequestBody AiChatRequest request,
            @AuthenticationPrincipal User user) {

        AiChatResponse response = aiChatService.processAiChat(user, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
