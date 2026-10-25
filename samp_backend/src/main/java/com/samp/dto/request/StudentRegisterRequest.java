package com.samp.dto.request;

import jakarta.validation.constraints.*;

public class StudentRegisterRequest {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, and one number"
    )
    private String password;

    @NotBlank(message = "Student ID is required")
    private String studentId;

    private String major;

    @Min(value = 1, message = "Year must be at least 1")
    @Max(value = 8, message = "Year must be at most 8")
    private Integer year;

    public StudentRegisterRequest() {}

    public StudentRegisterRequest(String firstName, String lastName, String email, String password, String studentId, String major, Integer year) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.studentId = studentId;
        this.major = major;
        this.year = year;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
}
