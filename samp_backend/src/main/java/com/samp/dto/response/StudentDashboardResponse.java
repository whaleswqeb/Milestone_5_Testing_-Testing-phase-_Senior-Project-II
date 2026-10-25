package com.samp.dto.response;

import java.util.List;

public class StudentDashboardResponse {

    private String studentName;
    private String studentNumber;
    private String major;
    private Integer year;
    private Double gpa;
    private int activeEnrollments;
    private int completedCourses;
    private int totalCreditsEarned;
    private long unreadNotifications;
    private List<NotificationResponse> recentNotifications;
    private List<EnrollmentResponse> currentEnrollments;

    public StudentDashboardResponse() {}

    public StudentDashboardResponse(String studentName, String studentNumber, String major, Integer year, Double gpa, int activeEnrollments, int completedCourses, int totalCreditsEarned, long unreadNotifications, List<NotificationResponse> recentNotifications, List<EnrollmentResponse> currentEnrollments) {
        this.studentName = studentName;
        this.studentNumber = studentNumber;
        this.major = major;
        this.year = year;
        this.gpa = gpa;
        this.activeEnrollments = activeEnrollments;
        this.completedCourses = completedCourses;
        this.totalCreditsEarned = totalCreditsEarned;
        this.unreadNotifications = unreadNotifications;
        this.recentNotifications = recentNotifications;
        this.currentEnrollments = currentEnrollments;
    }

    public static StudentDashboardResponseBuilder builder() {
        return new StudentDashboardResponseBuilder();
    }

    public static class StudentDashboardResponseBuilder {
        private String studentName;
        private String studentNumber;
        private String major;
        private Integer year;
        private Double gpa;
        private int activeEnrollments;
        private int completedCourses;
        private int totalCreditsEarned;
        private long unreadNotifications;
        private List<NotificationResponse> recentNotifications;
        private List<EnrollmentResponse> currentEnrollments;

        public StudentDashboardResponseBuilder studentName(String studentName) { this.studentName = studentName; return this; }
        public StudentDashboardResponseBuilder studentNumber(String studentNumber) { this.studentNumber = studentNumber; return this; }
        public StudentDashboardResponseBuilder major(String major) { this.major = major; return this; }
        public StudentDashboardResponseBuilder year(Integer year) { this.year = year; return this; }
        public StudentDashboardResponseBuilder gpa(Double gpa) { this.gpa = gpa; return this; }
        public StudentDashboardResponseBuilder activeEnrollments(int activeEnrollments) { this.activeEnrollments = activeEnrollments; return this; }
        public StudentDashboardResponseBuilder completedCourses(int completedCourses) { this.completedCourses = completedCourses; return this; }
        public StudentDashboardResponseBuilder totalCreditsEarned(int totalCreditsEarned) { this.totalCreditsEarned = totalCreditsEarned; return this; }
        public StudentDashboardResponseBuilder unreadNotifications(long unreadNotifications) { this.unreadNotifications = unreadNotifications; return this; }
        public StudentDashboardResponseBuilder recentNotifications(List<NotificationResponse> recentNotifications) { this.recentNotifications = recentNotifications; return this; }
        public StudentDashboardResponseBuilder currentEnrollments(List<EnrollmentResponse> currentEnrollments) { this.currentEnrollments = currentEnrollments; return this; }

        public StudentDashboardResponse build() {
            return new StudentDashboardResponse(studentName, studentNumber, major, year, gpa, activeEnrollments, completedCourses, totalCreditsEarned, unreadNotifications, recentNotifications, currentEnrollments);
        }
    }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Double getGpa() { return gpa; }
    public void setGpa(Double gpa) { this.gpa = gpa; }

    public int getActiveEnrollments() { return activeEnrollments; }
    public void setActiveEnrollments(int activeEnrollments) { this.activeEnrollments = activeEnrollments; }

    public int getCompletedCourses() { return completedCourses; }
    public void setCompletedCourses(int completedCourses) { this.completedCourses = completedCourses; }

    public int getTotalCreditsEarned() { return totalCreditsEarned; }
    public void setTotalCreditsEarned(int totalCreditsEarned) { this.totalCreditsEarned = totalCreditsEarned; }

    public long getUnreadNotifications() { return unreadNotifications; }
    public void setUnreadNotifications(long unreadNotifications) { this.unreadNotifications = unreadNotifications; }

    public List<NotificationResponse> getRecentNotifications() { return recentNotifications; }
    public void setRecentNotifications(List<NotificationResponse> recentNotifications) { this.recentNotifications = recentNotifications; }

    public List<EnrollmentResponse> getCurrentEnrollments() { return currentEnrollments; }
    public void setCurrentEnrollments(List<EnrollmentResponse> currentEnrollments) { this.currentEnrollments = currentEnrollments; }
}