package com.samp.dto.response;

import com.samp.enums.EnrollmentStatus;
import com.samp.enums.LetterGrade;

import java.time.LocalDateTime;

public class EnrollmentResponse {

    private Long id;

    // Student info
    private Long studentId;
    private String studentName;
    private String studentNumber;

    // Course info
    private Long courseId;
    private String courseCode;
    private String courseName;
    private Integer credits;
    private String semester;
    private String facultyName;

    // Enrollment state
    private EnrollmentStatus status;
    private LetterGrade grade;
    private Double gradePoints;

    private LocalDateTime enrolledAt;
    private LocalDateTime updatedAt;

    public EnrollmentResponse() {}

    public EnrollmentResponse(Long id, Long studentId, String studentName, String studentNumber, Long courseId, String courseCode, String courseName, Integer credits, String semester, String facultyName, EnrollmentStatus status, LetterGrade grade, Double gradePoints, LocalDateTime enrolledAt, LocalDateTime updatedAt) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentNumber = studentNumber;
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.semester = semester;
        this.facultyName = facultyName;
        this.status = status;
        this.grade = grade;
        this.gradePoints = gradePoints;
        this.enrolledAt = enrolledAt;
        this.updatedAt = updatedAt;
    }

    public static EnrollmentResponseBuilder builder() {
        return new EnrollmentResponseBuilder();
    }

    public static class EnrollmentResponseBuilder {
        private Long id;
        private Long studentId;
        private String studentName;
        private String studentNumber;
        private Long courseId;
        private String courseCode;
        private String courseName;
        private Integer credits;
        private String semester;
        private String facultyName;
        private EnrollmentStatus status;
        private LetterGrade grade;
        private Double gradePoints;
        private LocalDateTime enrolledAt;
        private LocalDateTime updatedAt;

        public EnrollmentResponseBuilder id(Long id) { this.id = id; return this; }
        public EnrollmentResponseBuilder studentId(Long studentId) { this.studentId = studentId; return this; }
        public EnrollmentResponseBuilder studentName(String studentName) { this.studentName = studentName; return this; }
        public EnrollmentResponseBuilder studentNumber(String studentNumber) { this.studentNumber = studentNumber; return this; }
        public EnrollmentResponseBuilder courseId(Long courseId) { this.courseId = courseId; return this; }
        public EnrollmentResponseBuilder courseCode(String courseCode) { this.courseCode = courseCode; return this; }
        public EnrollmentResponseBuilder courseName(String courseName) { this.courseName = courseName; return this; }
        public EnrollmentResponseBuilder credits(Integer credits) { this.credits = credits; return this; }
        public EnrollmentResponseBuilder semester(String semester) { this.semester = semester; return this; }
        public EnrollmentResponseBuilder facultyName(String facultyName) { this.facultyName = facultyName; return this; }
        public EnrollmentResponseBuilder status(EnrollmentStatus status) { this.status = status; return this; }
        public EnrollmentResponseBuilder grade(LetterGrade grade) { this.grade = grade; return this; }
        public EnrollmentResponseBuilder gradePoints(Double gradePoints) { this.gradePoints = gradePoints; return this; }
        public EnrollmentResponseBuilder enrolledAt(LocalDateTime enrolledAt) { this.enrolledAt = enrolledAt; return this; }
        public EnrollmentResponseBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public EnrollmentResponse build() {
            return new EnrollmentResponse(id, studentId, studentName, studentNumber, courseId, courseCode, courseName, credits, semester, facultyName, status, grade, gradePoints, enrolledAt, updatedAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public String getFacultyName() { return facultyName; }
    public void setFacultyName(String facultyName) { this.facultyName = facultyName; }

    public EnrollmentStatus getStatus() { return status; }
    public void setStatus(EnrollmentStatus status) { this.status = status; }

    public LetterGrade getGrade() { return grade; }
    public void setGrade(LetterGrade grade) { this.grade = grade; }

    public Double getGradePoints() { return gradePoints; }
    public void setGradePoints(Double gradePoints) { this.gradePoints = gradePoints; }

    public LocalDateTime getEnrolledAt() { return enrolledAt; }
    public void setEnrolledAt(LocalDateTime enrolledAt) { this.enrolledAt = enrolledAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}