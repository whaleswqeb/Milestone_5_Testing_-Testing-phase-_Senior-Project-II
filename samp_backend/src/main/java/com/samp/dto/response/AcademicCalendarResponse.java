package com.samp.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AcademicCalendarResponse {

    private Long id;
    private String semesterName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate enrollmentOpenDate;
    private LocalDate enrollmentCloseDate;
    private LocalDate dropDeadline;
    private boolean published;
    private LocalDateTime createdAt;

    public AcademicCalendarResponse() {}

    public AcademicCalendarResponse(Long id, String semesterName, LocalDate startDate, LocalDate endDate, LocalDate enrollmentOpenDate, LocalDate enrollmentCloseDate, LocalDate dropDeadline, boolean published, LocalDateTime createdAt) {
        this.id = id;
        this.semesterName = semesterName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.enrollmentOpenDate = enrollmentOpenDate;
        this.enrollmentCloseDate = enrollmentCloseDate;
        this.dropDeadline = dropDeadline;
        this.published = published;
        this.createdAt = createdAt;
    }

    public static AcademicCalendarResponseBuilder builder() {
        return new AcademicCalendarResponseBuilder();
    }

    public static class AcademicCalendarResponseBuilder {
        private Long id;
        private String semesterName;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalDate enrollmentOpenDate;
        private LocalDate enrollmentCloseDate;
        private LocalDate dropDeadline;
        private boolean published;
        private LocalDateTime createdAt;

        public AcademicCalendarResponseBuilder id(Long id) { this.id = id; return this; }
        public AcademicCalendarResponseBuilder semesterName(String semesterName) { this.semesterName = semesterName; return this; }
        public AcademicCalendarResponseBuilder startDate(LocalDate startDate) { this.startDate = startDate; return this; }
        public AcademicCalendarResponseBuilder endDate(LocalDate endDate) { this.endDate = endDate; return this; }
        public AcademicCalendarResponseBuilder enrollmentOpenDate(LocalDate enrollmentOpenDate) { this.enrollmentOpenDate = enrollmentOpenDate; return this; }
        public AcademicCalendarResponseBuilder enrollmentCloseDate(LocalDate enrollmentCloseDate) { this.enrollmentCloseDate = enrollmentCloseDate; return this; }
        public AcademicCalendarResponseBuilder dropDeadline(LocalDate dropDeadline) { this.dropDeadline = dropDeadline; return this; }
        public AcademicCalendarResponseBuilder published(boolean published) { this.published = published; return this; }
        public AcademicCalendarResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public AcademicCalendarResponse build() {
            return new AcademicCalendarResponse(id, semesterName, startDate, endDate, enrollmentOpenDate, enrollmentCloseDate, dropDeadline, published, createdAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public boolean isPublished() { return published; }
    public void setPublished(boolean published) { this.published = published; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}