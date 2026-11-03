
package com.example.student.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;

    @NotBlank(message = "Student name cannot be empty")
    private String studentName;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter valid email")
    private String email;

    @Min(value = 18, message = "Age must be at least 18")
    private int age;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // Default constructor
    public Student() {
    }

    // Getters and Setters

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
