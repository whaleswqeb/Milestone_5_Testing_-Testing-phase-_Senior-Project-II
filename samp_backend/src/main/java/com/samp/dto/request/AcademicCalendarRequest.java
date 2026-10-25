package com.samp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class AcademicCalendarRequest {

    @NotBlank(message = "Semester name is required")
    private String semesterName;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotNull(message = "Enrollment open date is required")
    private LocalDate enrollmentOpenDate;

    @NotNull(message = "Enrollment close date is required")
    private LocalDate enrollmentCloseDate;

    @NotNull(message = "Drop deadline is required")
    private LocalDate dropDeadline;

    public AcademicCalendarRequest() {}

    public AcademicCalendarRequest(String semesterName, LocalDate startDate, LocalDate endDate, LocalDate enrollmentOpenDate, LocalDate enrollmentCloseDate, LocalDate dropDeadline) {
        this.semesterName = semesterName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.enrollmentOpenDate = enrollmentOpenDate;
        this.enrollmentCloseDate = enrollmentCloseDate;
        this.dropDeadline = dropDeadline;
    }

    public String getSemesterName() { return semesterName; }
    public void setSemesterName(String semesterName) { this.semesterName = semesterName; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public LocalDate getEnrollmentOpenDate() { return enrollmentOpenDate; }
    public void setEnrollmentOpenDate(LocalDate enrollmentOpenDate) { this.enrollmentOpenDate = enrollmentOpenDate; }

    public LocalDate getEnrollmentCloseDate() { return enrollmentCloseDate; }
    public void setEnrollmentCloseDate(LocalDate enrollmentCloseDate) { this.enrollmentCloseDate = enrollmentCloseDate; }

    public LocalDate getDropDeadline() { return dropDeadline; }
    public void setDropDeadline(LocalDate dropDeadline) { this.dropDeadline = dropDeadline; }
}