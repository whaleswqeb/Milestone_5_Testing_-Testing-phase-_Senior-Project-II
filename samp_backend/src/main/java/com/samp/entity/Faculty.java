package com.samp.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "faculty")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String department;

    private String title;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<>();

    public Faculty() {}

    public Faculty(Long id, User user, String department, String title, List<Course> courses) {
        this.id = id;
        this.user = user;
        this.department = department;
        this.title = title;
        this.courses = courses != null ? courses : new ArrayList<>();
    }

    public static FacultyBuilder builder() {
        return new FacultyBuilder();
    }

    public static class FacultyBuilder {
        private Long id;
        private User user;
        private String department;
        private String title;
        private List<Course> courses = new ArrayList<>();

        public FacultyBuilder id(Long id) { this.id = id; return this; }
        public FacultyBuilder user(User user) { this.user = user; return this; }
        public FacultyBuilder department(String department) { this.department = department; return this; }
        public FacultyBuilder title(String title) { this.title = title; return this; }
        public FacultyBuilder courses(List<Course> courses) { this.courses = courses; return this; }

        public Faculty build() {
            return new Faculty(id, user, department, title, courses);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<Course> getCourses() { return courses; }
    public void setCourses(List<Course> courses) { this.courses = courses; }
}
