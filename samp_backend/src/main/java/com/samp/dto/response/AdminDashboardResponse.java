package com.samp.dto.response;

public class AdminDashboardResponse {

    private long totalStudents;
    private long totalFaculty;
    private long totalCourses;
    private long totalActiveCourses;
    private long totalActiveEnrollments;
    private long totalUsers;

    public AdminDashboardResponse() {}

    public AdminDashboardResponse(long totalStudents, long totalFaculty, long totalCourses, long totalActiveCourses, long totalActiveEnrollments, long totalUsers) {
        this.totalStudents = totalStudents;
        this.totalFaculty = totalFaculty;
        this.totalCourses = totalCourses;
        this.totalActiveCourses = totalActiveCourses;
        this.totalActiveEnrollments = totalActiveEnrollments;
        this.totalUsers = totalUsers;
    }

    public static AdminDashboardResponseBuilder builder() {
        return new AdminDashboardResponseBuilder();
    }

    public static class AdminDashboardResponseBuilder {
        private long totalStudents;
        private long totalFaculty;
        private long totalCourses;
        private long totalActiveCourses;
        private long totalActiveEnrollments;
        private long totalUsers;

        public AdminDashboardResponseBuilder totalStudents(long totalStudents) { this.totalStudents = totalStudents; return this; }
        public AdminDashboardResponseBuilder totalFaculty(long totalFaculty) { this.totalFaculty = totalFaculty; return this; }
        public AdminDashboardResponseBuilder totalCourses(long totalCourses) { this.totalCourses = totalCourses; return this; }
        public AdminDashboardResponseBuilder totalActiveCourses(long totalActiveCourses) { this.totalActiveCourses = totalActiveCourses; return this; }
        public AdminDashboardResponseBuilder totalActiveEnrollments(long totalActiveEnrollments) { this.totalActiveEnrollments = totalActiveEnrollments; return this; }
        public AdminDashboardResponseBuilder totalUsers(long totalUsers) { this.totalUsers = totalUsers; return this; }

        public AdminDashboardResponse build() {
            return new AdminDashboardResponse(totalStudents, totalFaculty, totalCourses, totalActiveCourses, totalActiveEnrollments, totalUsers);
        }
    }

    public long getTotalStudents() { return totalStudents; }
    public void setTotalStudents(long totalStudents) { this.totalStudents = totalStudents; }

    public long getTotalFaculty() { return totalFaculty; }
    public void setTotalFaculty(long totalFaculty) { this.totalFaculty = totalFaculty; }

    public long getTotalCourses() { return totalCourses; }
    public void setTotalCourses(long totalCourses) { this.totalCourses = totalCourses; }

    public long getTotalActiveCourses() { return totalActiveCourses; }
    public void setTotalActiveCourses(long totalActiveCourses) { this.totalActiveCourses = totalActiveCourses; }

    public long getTotalActiveEnrollments() { return totalActiveEnrollments; }
    public void setTotalActiveEnrollments(long totalActiveEnrollments) { this.totalActiveEnrollments = totalActiveEnrollments; }

    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }
}