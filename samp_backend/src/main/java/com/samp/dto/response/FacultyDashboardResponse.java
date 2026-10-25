package com.samp.dto.response;

import java.util.List;

public class FacultyDashboardResponse {

    private String facultyName;
    private String department;
    private String title;
    private int totalCourses;
    private int totalStudentsEnrolled;
    private int pendingGradeEntries;
    private List<CourseResponse> myCourses;

    public FacultyDashboardResponse() {}

    public FacultyDashboardResponse(String facultyName, String department, String title, int totalCourses, int totalStudentsEnrolled, int pendingGradeEntries, List<CourseResponse> myCourses) {
        this.facultyName = facultyName;
        this.department = department;
        this.title = title;
        this.totalCourses = totalCourses;
        this.totalStudentsEnrolled = totalStudentsEnrolled;
        this.pendingGradeEntries = pendingGradeEntries;
        this.myCourses = myCourses;
    }

    public static FacultyDashboardResponseBuilder builder() {
        return new FacultyDashboardResponseBuilder();
    }

    public static class FacultyDashboardResponseBuilder {
        private String facultyName;
        private String department;
        private String title;
        private int totalCourses;
        private int totalStudentsEnrolled;
        private int pendingGradeEntries;
        private List<CourseResponse> myCourses;

        public FacultyDashboardResponseBuilder facultyName(String facultyName) { this.facultyName = facultyName; return this; }
        public FacultyDashboardResponseBuilder department(String department) { this.department = department; return this; }
        public FacultyDashboardResponseBuilder title(String title) { this.title = title; return this; }
        public FacultyDashboardResponseBuilder totalCourses(int totalCourses) { this.totalCourses = totalCourses; return this; }
        public FacultyDashboardResponseBuilder totalStudentsEnrolled(int totalStudentsEnrolled) { this.totalStudentsEnrolled = totalStudentsEnrolled; return this; }
        public FacultyDashboardResponseBuilder pendingGradeEntries(int pendingGradeEntries) { this.pendingGradeEntries = pendingGradeEntries; return this; }
        public FacultyDashboardResponseBuilder myCourses(List<CourseResponse> myCourses) { this.myCourses = myCourses; return this; }

        public FacultyDashboardResponse build() {
            return new FacultyDashboardResponse(facultyName, department, title, totalCourses, totalStudentsEnrolled, pendingGradeEntries, myCourses);
        }
    }

    public String getFacultyName() { return facultyName; }
    public void setFacultyName(String facultyName) { this.facultyName = facultyName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getTotalCourses() { return totalCourses; }
    public void setTotalCourses(int totalCourses) { this.totalCourses = totalCourses; }

    public int getTotalStudentsEnrolled() { return totalStudentsEnrolled; }
    public void setTotalStudentsEnrolled(int totalStudentsEnrolled) { this.totalStudentsEnrolled = totalStudentsEnrolled; }

    public int getPendingGradeEntries() { return pendingGradeEntries; }
    public void setPendingGradeEntries(int pendingGradeEntries) { this.pendingGradeEntries = pendingGradeEntries; }

    public List<CourseResponse> getMyCourses() { return myCourses; }
    public void setMyCourses(List<CourseResponse> myCourses) { this.myCourses = myCourses; }
}