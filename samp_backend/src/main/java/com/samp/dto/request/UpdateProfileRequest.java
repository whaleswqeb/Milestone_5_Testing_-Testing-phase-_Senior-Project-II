package com.samp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UpdateProfileRequest {

    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @Email(message = "Invalid email format")
    private String email;

    // Required only when changing password
    private String currentPassword;

    @Size(min = 8, message = "New password must be at least 8 characters")
    private String newPassword;

    // Faculty-specific
    private String department;
    private String title;

    // Student-specific
    private String major;
    private Integer year;

    public UpdateProfileRequest() {}

    public UpdateProfileRequest(String firstName, String lastName, String email, String currentPassword, String newPassword, String department, String title, String major, Integer year) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.department = department;
        this.title = title;
        this.major = major;
        this.year = year;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCurrentPassword() { return currentPassword; }
    public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }

    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
}