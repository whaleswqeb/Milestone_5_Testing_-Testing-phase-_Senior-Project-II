package com.samp.dto.request;

import com.samp.enums.LetterGrade;
import jakarta.validation.constraints.NotNull;

public class GradeRequest {

    @NotNull(message = "Enrollment ID is required")
    private Long enrollmentId;

    @NotNull(message = "Grade is required")
    private LetterGrade grade;

    public GradeRequest() {}

    public GradeRequest(Long enrollmentId, LetterGrade grade) {
        this.enrollmentId = enrollmentId;
        this.grade = grade;
    }

    public Long getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(Long enrollmentId) { this.enrollmentId = enrollmentId; }

    public LetterGrade getGrade() { return grade; }
    public void setGrade(LetterGrade grade) { this.grade = grade; }
}