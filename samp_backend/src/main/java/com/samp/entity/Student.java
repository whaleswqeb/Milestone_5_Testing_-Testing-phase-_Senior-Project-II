package com.samp.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false,unique = true)
    private User user;

    @Column(unique = true,nullable = false)
    private String studentId;

    private String major;

    private Integer year;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();

    public Student() {}

    public Student(Long id, User user, String studentId, String major, Integer year, List<Enrollment> enrollments) {
        this.id = id;
        this.user = user;
        this.studentId = studentId;
        this.major = major;
        this.year = year;
        this.enrollments = enrollments != null ? enrollments : new ArrayList<>();
    }

    public static StudentBuilder builder() {
        return new StudentBuilder();
    }

    public static class StudentBuilder {
        private Long id;
        private User user;
        private String studentId;
        private String major;
        private Integer year;
        private List<Enrollment> enrollments = new ArrayList<>();

        public StudentBuilder id(Long id) { this.id = id; return this; }
        public StudentBuilder user(User user) { this.user = user; return this; }
        public StudentBuilder studentId(String studentId) { this.studentId = studentId; return this; }
        public StudentBuilder major(String major) { this.major = major; return this; }
        public StudentBuilder year(Integer year) { this.year = year; return this; }
        public StudentBuilder enrollments(List<Enrollment> enrollments) { this.enrollments = enrollments; return this; }

        public Student build() {
            return new Student(id, user, studentId, major, year, enrollments);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public List<Enrollment> getEnrollments() { return enrollments; }
    public void setEnrollments(List<Enrollment> enrollments) { this.enrollments = enrollments; }
}
