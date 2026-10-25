package com.samp.dto.response;

import java.time.LocalDateTime;

public class CourseResponse {

    private Long id;
    private String courseCode;
    private String courseName;
    private String description;
    private Integer credits;
    private Integer maxCapacity;
    private Integer enrolledCount;
    private Integer availableSeats;
    private String semester;
    private boolean active;
    private Long facultyId;
    private String facultyName;
    private LocalDateTime createdAt;

    public CourseResponse() {}

    public CourseResponse(Long id, String courseCode, String courseName, String description, Integer credits, Integer maxCapacity, Integer enrolledCount, Integer availableSeats, String semester, boolean active, Long facultyId, String facultyName, LocalDateTime createdAt) {
        this.id = id;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.description = description;
        this.credits = credits;
        this.maxCapacity = maxCapacity;
        this.enrolledCount = enrolledCount;
        this.availableSeats = availableSeats;
        this.semester = semester;
        this.active = active;
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.createdAt = createdAt;
    }

    public static CourseResponseBuilder builder() {
        return new CourseResponseBuilder();
    }

    public static class CourseResponseBuilder {
        private Long id;
        private String courseCode;
        private String courseName;
        private String description;
        private Integer credits;
        private Integer maxCapacity;
        private Integer enrolledCount;
        private Integer availableSeats;
        private String semester;
        private boolean active;
        private Long facultyId;
        private String facultyName;
        private LocalDateTime createdAt;

        public CourseResponseBuilder id(Long id) { this.id = id; return this; }
        public CourseResponseBuilder courseCode(String courseCode) { this.courseCode = courseCode; return this; }
        public CourseResponseBuilder courseName(String courseName) { this.courseName = courseName; return this; }
        public CourseResponseBuilder description(String description) { this.description = description; return this; }
        public CourseResponseBuilder credits(Integer credits) { this.credits = credits; return this; }
        public CourseResponseBuilder maxCapacity(Integer maxCapacity) { this.maxCapacity = maxCapacity; return this; }
        public CourseResponseBuilder enrolledCount(Integer enrolledCount) { this.enrolledCount = enrolledCount; return this; }
        public CourseResponseBuilder availableSeats(Integer availableSeats) { this.availableSeats = availableSeats; return this; }
        public CourseResponseBuilder semester(String semester) { this.semester = semester; return this; }
        public CourseResponseBuilder active(boolean active) { this.active = active; return this; }
        public CourseResponseBuilder facultyId(Long facultyId) { this.facultyId = facultyId; return this; }
        public CourseResponseBuilder facultyName(String facultyName) { this.facultyName = facultyName; return this; }
        public CourseResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public CourseResponse build() {
            return new CourseResponse(id, courseCode, courseName, description, credits, maxCapacity, enrolledCount, availableSeats, semester, active, facultyId, facultyName, createdAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }

    public Integer getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(Integer maxCapacity) { this.maxCapacity = maxCapacity; }

    public Integer getEnrolledCount() { return enrolledCount; }
    public void setEnrolledCount(Integer enrolledCount) { this.enrolledCount = enrolledCount; }

    public Integer getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public Long getFacultyId() { return facultyId; }
    public void setFacultyId(Long facultyId) { this.facultyId = facultyId; }

    public String getFacultyName() { return facultyName; }
    public void setFacultyName(String facultyName) { this.facultyName = facultyName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}