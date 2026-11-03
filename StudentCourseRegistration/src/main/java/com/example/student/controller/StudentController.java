
package com.example.student.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.student.entity.Student;
import com.example.student.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student addStudent(@Valid @RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {
        return studentService.getStudentById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable int id) {
        return studentService.deleteStudent(id);
    }
}
