package com.samp.controller;

import com.samp.dto.request.UpdateProfileRequest;
import com.samp.dto.response.*;
import com.samp.entity.User;
import com.samp.service.impl.EnrollmentService;
import com.samp.service.impl.CourseService;
import com.samp.service.impl.NotificationService;
import com.samp.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@Tag(name = "Student", description = "Student endpoints")
public class StudentController {

    private final EnrollmentService enrollmentService;
    private final CourseService courseService;
    private final NotificationService notificationService;
    private final UserService userService;

    public StudentController(EnrollmentService enrollmentService,
                             CourseService courseService,
                             NotificationService notificationService,
                             UserService userService) {
        this.enrollmentService = enrollmentService;
        this.courseService = courseService;
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    @Operation(summary = "Get student dashboard data")
    public ResponseEntity<ApiResponse<StudentDashboardResponse>> dashboard(
            @AuthenticationPrincipal User user) {

        double gpa = enrollmentService.calculateGpa(user);
        int activeEnrollments = enrollmentService
                .getMyActiveEnrollments(user).size();
        int creditsEarned = enrollmentService.calculateCreditsEarned(user);
        long unread = notificationService.getUnreadCount(user);
        List<NotificationResponse> recentNotifs = notificationService
                .getAllNotifications(user)
                .stream().limit(3).toList();
        List<EnrollmentResponse> currentEnrollments = enrollmentService
                .getMyActiveEnrollments(user);

        StudentDashboardResponse dashboard = StudentDashboardResponse.builder()
                .studentName(user.getFullName())
                .gpa(gpa)
                .activeEnrollments(activeEnrollments)
                .totalCreditsEarned(creditsEarned)
                .unreadNotifications(unread)
                .recentNotifications(recentNotifs)
                .currentEnrollments(currentEnrollments)
                .build();

        return ResponseEntity.ok(ApiResponse.success(dashboard));
    }

    @GetMapping("/courses")
    @Operation(summary = "Browse all active courses")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getCourses() {
        return ResponseEntity.ok(
                ApiResponse.success(courseService.getAllActiveCourses())
        );
    }

    @PostMapping("/enrollments/{courseId}")
    @Operation(summary = "Enroll in a course")
    public ResponseEntity<ApiResponse<EnrollmentResponse>> enroll(
            @PathVariable Long courseId,
            @AuthenticationPrincipal User user) {

        EnrollmentResponse response =
                enrollmentService.enroll(courseId, user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Successfully enrolled in course", response
                ));
    }

    @GetMapping("/enrollments")
    @Operation(summary = "Get all my enrollments")
    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getEnrollments(
            @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(
                ApiResponse.success(enrollmentService.getMyEnrollments(user))
        );
    }

    @PatchMapping("/enrollments/{courseId}/drop")
    @Operation(summary = "Drop an active course")
    public ResponseEntity<ApiResponse<EnrollmentResponse>> drop(
            @PathVariable Long courseId,
            @AuthenticationPrincipal User user) {

        EnrollmentResponse response =
                enrollmentService.drop(courseId, user);
        return ResponseEntity.ok(
                ApiResponse.success("Course dropped successfully", response)
        );
    }

    @GetMapping("/grades")
    @Operation(summary = "View all grades and GPA")
    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getGrades(
            @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(
                ApiResponse.success(enrollmentService.getMyEnrollments(user))
        );
    }

    @GetMapping("/profile")
    @Operation(summary = "Get my profile")
    public ResponseEntity<ApiResponse<UserResponse>> getProfile(
            @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(
                ApiResponse.success(userService.toResponse(user))
        );
    }

    @PutMapping("/profile")
    @Operation(summary = "Update my profile")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request,
            @AuthenticationPrincipal User user) {

        UserResponse response =
                userService.updateProfile(user, request);
        return ResponseEntity.ok(
                ApiResponse.success("Profile updated successfully", response)
        );
    }
}