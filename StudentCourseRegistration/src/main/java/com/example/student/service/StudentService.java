
package com.example.student.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.student.entity.Student;
import com.example.student.entity.Course;
import com.example.student.repository.StudentRepository;
import com.example.student.repository.CourseRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository,
                          CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public Student addStudent(Student student) {

        int courseId = student.getCourse().getCourseId();

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                    new RuntimeException("Course not found"));

        student.setCourse(course);

        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(int id) {
        return studentRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Student not found"));
    }

    public String deleteStudent(int id) {
        studentRepository.deleteById(id);
        return "Student deleted successfully";
    }
}
