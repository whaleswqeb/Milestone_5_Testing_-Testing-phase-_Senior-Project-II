package com.samp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String courseCode;

    @Column(nullable = false)
    private String courseName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer credits;

    @Column(nullable = false)
    private Integer maxCapacity;

    @Column(nullable = false)
    private String semester;

    @Column(nullable = false)
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Course() {}

    public Course(Long id, String courseCode, String courseName, String description, Integer credits, Integer maxCapacity, String semester, boolean active, Faculty faculty, List<Enrollment> enrollments, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.description = description;
        this.credits = credits;
        this.maxCapacity = maxCapacity;
        this.semester = semester;
        this.active = active;
        this.faculty = faculty;
        this.enrollments = enrollments != null ? enrollments : new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CourseBuilder builder() {
        return new CourseBuilder();
    }

    public static class CourseBuilder {
        private Long id;
        private String courseCode;
        private String courseName;
        private String description;
        private Integer credits;
        private Integer maxCapacity;
        private String semester;
        private boolean active = true;
        private Faculty faculty;
        private List<Enrollment> enrollments = new ArrayList<>();
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public CourseBuilder id(Long id) { this.id = id; return this; }
        public CourseBuilder courseCode(String courseCode) { this.courseCode = courseCode; return this; }
        public CourseBuilder courseName(String courseName) { this.courseName = courseName; return this; }
        public CourseBuilder description(String description) { this.description = description; return this; }
        public CourseBuilder credits(Integer credits) { this.credits = credits; return this; }
        public CourseBuilder maxCapacity(Integer maxCapacity) { this.maxCapacity = maxCapacity; return this; }
        public CourseBuilder semester(String semester) { this.semester = semester; return this; }
        public CourseBuilder active(boolean active) { this.active = active; return this; }
        public CourseBuilder faculty(Faculty faculty) { this.faculty = faculty; return this; }
        public CourseBuilder enrollments(List<Enrollment> enrollments) { this.enrollments = enrollments; return this; }
        public CourseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public CourseBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Course build() {
            return new Course(id, courseCode, courseName, description, credits, maxCapacity, semester, active, faculty, enrollments, createdAt, updatedAt);
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

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public Faculty getFaculty() { return faculty; }
    public void setFaculty(Faculty faculty) { this.faculty = faculty; }

    public List<Enrollment> getEnrollments() { return enrollments; }
    public void setEnrollments(List<Enrollment> enrollments) { this.enrollments = enrollments; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
