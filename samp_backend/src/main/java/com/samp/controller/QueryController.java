package com.samp.controller;

import com.samp.dto.request.CreateQueryRequest;
import com.samp.dto.request.QueryMessageRequest;
import com.samp.dto.response.ApiResponse;
import com.samp.dto.response.QueryMessageResponse;
import com.samp.dto.response.StudentQueryResponse;
import com.samp.entity.User;
import com.samp.enums.QueryStatus;
import com.samp.service.impl.QueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/queries")
@Tag(name = "Mailbox & Queries", description = "Student Query & Mailbox System Endpoints")
public class QueryController {

    private final QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Raise a new query (Student)")
    public ResponseEntity<ApiResponse<StudentQueryResponse>> createQuery(
            @Valid @RequestBody CreateQueryRequest request,
            @AuthenticationPrincipal User user) {

        StudentQueryResponse response = queryService.createQuery(user, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Query raised successfully and dispatched to event bus", response));
    }

    @PostMapping("/{queryId}/messages")
    @Operation(summary = "Reply to a query thread")
    public ResponseEntity<ApiResponse<QueryMessageResponse>> addMessage(
            @PathVariable Long queryId,
            @Valid @RequestBody QueryMessageRequest request,
            @AuthenticationPrincipal User user) {

        QueryMessageResponse response = queryService.addMessage(queryId, user, request);
        return ResponseEntity.ok(ApiResponse.success("Message sent", response));
    }

    @GetMapping
    @Operation(summary = "Get user queries (Returns student's queries or all queries for Faculty/Admin)")
    public ResponseEntity<ApiResponse<List<StudentQueryResponse>>> getQueries(
            @AuthenticationPrincipal User user) {

        List<StudentQueryResponse> responses;
        if (user.getRole().name().equals("STUDENT")) {
            responses = queryService.getStudentQueries(user);
        } else if (user.getRole().name().equals("FACULTY")) {
            responses = queryService.getAssignedQueries(user);
            if (responses.isEmpty()) {
                responses = queryService.getAllQueries();
            }
        } else {
            responses = queryService.getAllQueries();
        }

        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/{queryId}")
    @Operation(summary = "Get query details with messages")
    public ResponseEntity<ApiResponse<StudentQueryResponse>> getQueryById(
            @PathVariable Long queryId,
            @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(ApiResponse.success(queryService.getQueryById(queryId, user)));
    }

    @PatchMapping("/{queryId}/status")
    @Operation(summary = "Update query status (e.g. IN_PROGRESS, RESOLVED, CLOSED)")
    public ResponseEntity<ApiResponse<StudentQueryResponse>> updateStatus(
            @PathVariable Long queryId,
            @RequestParam QueryStatus status,
            @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(ApiResponse.success("Query status updated", queryService.updateStatus(queryId, status, user)));
    }
}
