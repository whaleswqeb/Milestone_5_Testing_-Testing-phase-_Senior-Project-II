package com.samp.entity;

import com.samp.enums.EnrollmentStatus;
import com.samp.enums.LetterGrade;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}))
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus status = EnrollmentStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    private LetterGrade grade;

    @Column(updatable = false)
    private LocalDateTime enrolledAt;

    private LocalDateTime updatedAt;

    public Enrollment() {}

    public Enrollment(Long id, Student student, Course course, EnrollmentStatus status, LetterGrade grade, LocalDateTime enrolledAt, LocalDateTime updatedAt) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.status = status != null ? status : EnrollmentStatus.ACTIVE;
        this.grade = grade;
        this.enrolledAt = enrolledAt;
        this.updatedAt = updatedAt;
    }

    public static EnrollmentBuilder builder() {
        return new EnrollmentBuilder();
    }

    public static class EnrollmentBuilder {
        private Long id;
        private Student student;
        private Course course;
        private EnrollmentStatus status = EnrollmentStatus.ACTIVE;
        private LetterGrade grade;
        private LocalDateTime enrolledAt;
        private LocalDateTime updatedAt;

        public EnrollmentBuilder id(Long id) { this.id = id; return this; }
        public EnrollmentBuilder student(Student student) { this.student = student; return this; }
        public EnrollmentBuilder course(Course course) { this.course = course; return this; }
        public EnrollmentBuilder status(EnrollmentStatus status) { this.status = status; return this; }
        public EnrollmentBuilder grade(LetterGrade grade) { this.grade = grade; return this; }
        public EnrollmentBuilder enrolledAt(LocalDateTime enrolledAt) { this.enrolledAt = enrolledAt; return this; }
        public EnrollmentBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Enrollment build() {
            return new Enrollment(id, student, course, status, grade, enrolledAt, updatedAt);
        }
    }

    @PrePersist
    protected void onCreate() {
        enrolledAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public EnrollmentStatus getStatus() { return status; }
    public void setStatus(EnrollmentStatus status) { this.status = status; }

    public LetterGrade getGrade() { return grade; }
    public void setGrade(LetterGrade grade) { this.grade = grade; }

    public LocalDateTime getEnrolledAt() { return enrolledAt; }
    public void setEnrolledAt(LocalDateTime enrolledAt) { this.enrolledAt = enrolledAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
