package com.samp.dto.request;

import jakarta.validation.constraints.*;

public class CourseRequest {

    @NotBlank(message = "Course code is required")
    @Size(max = 20, message = "Course code must not exceed 20 characters")
    private String courseCode;

    @NotBlank(message = "Course name is required")
    @Size(max = 200, message = "Course name must not exceed 200 characters")
    private String courseName;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "Credits are required")
    @Min(value = 1, message = "Credits must be at least 1")
    @Max(value = 4, message = "Credits must not exceed 4")
    private Integer credits;

    @NotNull(message = "Max capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    @Max(value = 500, message = "Capacity must not exceed 500")
    private Integer maxCapacity;

    @NotBlank(message = "Semester is required")
    private String semester;

    // Optional — admin can assign any faculty, faculty gets auto-assigned
    private Long facultyId;

    public CourseRequest() {}

    public CourseRequest(String courseCode, String courseName, String description, Integer credits, Integer maxCapacity, String semester, Long facultyId) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.description = description;
        this.credits = credits;
        this.maxCapacity = maxCapacity;
        this.semester = semester;
        this.facultyId = facultyId;
    }

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

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public Long getFacultyId() { return facultyId; }
    public void setFacultyId(Long facultyId) { this.facultyId = facultyId; }
}
