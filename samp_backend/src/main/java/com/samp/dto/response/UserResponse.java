package com.samp.dto.response;

import com.samp.enums.Role;
import java.time.LocalDateTime;

public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private Role role;
    private boolean active;
    private LocalDateTime createdAt;

    // Student-specific (null for other roles)
    private String studentId;
    private String major;
    private Integer year;

    // Faculty-specific (null for other roles)
    private Long facultyProfileId;
    private String department;
    private String title;

    public UserResponse() {}

    public UserResponse(Long id, String firstName, String lastName, String fullName, String email, Role role, boolean active, LocalDateTime createdAt, String studentId, String major, Integer year, Long facultyProfileId, String department, String title) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.active = active;
        this.createdAt = createdAt;
        this.studentId = studentId;
        this.major = major;
        this.year = year;
        this.facultyProfileId = facultyProfileId;
        this.department = department;
        this.title = title;
    }

    public static UserResponseBuilder builder() {
        return new UserResponseBuilder();
    }

    public static class UserResponseBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private String fullName;
        private String email;
        private Role role;
        private boolean active;
        private LocalDateTime createdAt;
        private String studentId;
        private String major;
        private Integer year;
        private Long facultyProfileId;
        private String department;
        private String title;

        public UserResponseBuilder id(Long id) { this.id = id; return this; }
        public UserResponseBuilder firstName(String firstName) { this.firstName = firstName; return this; }
        public UserResponseBuilder lastName(String lastName) { this.lastName = lastName; return this; }
        public UserResponseBuilder fullName(String fullName) { this.fullName = fullName; return this; }
        public UserResponseBuilder email(String email) { this.email = email; return this; }
        public UserResponseBuilder role(Role role) { this.role = role; return this; }
        public UserResponseBuilder active(boolean active) { this.active = active; return this; }
        public UserResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public UserResponseBuilder studentId(String studentId) { this.studentId = studentId; return this; }
        public UserResponseBuilder major(String major) { this.major = major; return this; }
        public UserResponseBuilder year(Integer year) { this.year = year; return this; }
        public UserResponseBuilder facultyProfileId(Long facultyProfileId) { this.facultyProfileId = facultyProfileId; return this; }
        public UserResponseBuilder department(String department) { this.department = department; return this; }
        public UserResponseBuilder title(String title) { this.title = title; return this; }

        public UserResponse build() {
            return new UserResponse(id, firstName, lastName, fullName, email, role, active, createdAt, studentId, major, year, facultyProfileId, department, title);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Long getFacultyProfileId() { return facultyProfileId; }
    public void setFacultyProfileId(Long facultyProfileId) { this.facultyProfileId = facultyProfileId; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}