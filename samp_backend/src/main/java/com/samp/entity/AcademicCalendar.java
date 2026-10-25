package com.samp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "academic_calendars")
public class AcademicCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String semesterName;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private LocalDate enrollmentOpenDate;

    @Column(nullable = false)
    private LocalDate enrollmentCloseDate;

    @Column(nullable = false)
    private LocalDate dropDeadline;

    @Column(nullable = false)
    private boolean published = false;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public AcademicCalendar() {}

    public AcademicCalendar(Long id, String semesterName, LocalDate startDate, LocalDate endDate, LocalDate enrollmentOpenDate, LocalDate enrollmentCloseDate, LocalDate dropDeadline, boolean published, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.semesterName = semesterName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.enrollmentOpenDate = enrollmentOpenDate;
        this.enrollmentCloseDate = enrollmentCloseDate;
        this.dropDeadline = dropDeadline;
        this.published = published;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static AcademicCalendarBuilder builder() {
        return new AcademicCalendarBuilder();
    }

    public static class AcademicCalendarBuilder {
        private Long id;
        private String semesterName;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalDate enrollmentOpenDate;
        private LocalDate enrollmentCloseDate;
        private LocalDate dropDeadline;
        private boolean published = false;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public AcademicCalendarBuilder id(Long id) { this.id = id; return this; }
        public AcademicCalendarBuilder semesterName(String semesterName) { this.semesterName = semesterName; return this; }
        public AcademicCalendarBuilder startDate(LocalDate startDate) { this.startDate = startDate; return this; }
        public AcademicCalendarBuilder endDate(LocalDate endDate) { this.endDate = endDate; return this; }
        public AcademicCalendarBuilder enrollmentOpenDate(LocalDate enrollmentOpenDate) { this.enrollmentOpenDate = enrollmentOpenDate; return this; }
        public AcademicCalendarBuilder enrollmentCloseDate(LocalDate enrollmentCloseDate) { this.enrollmentCloseDate = enrollmentCloseDate; return this; }
        public AcademicCalendarBuilder dropDeadline(LocalDate dropDeadline) { this.dropDeadline = dropDeadline; return this; }
        public AcademicCalendarBuilder published(boolean published) { this.published = published; return this; }
        public AcademicCalendarBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public AcademicCalendarBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public AcademicCalendar build() {
            return new AcademicCalendar(id, semesterName, startDate, endDate, enrollmentOpenDate, enrollmentCloseDate, dropDeadline, published, createdAt, updatedAt);
        }
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
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

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}