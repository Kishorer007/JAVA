
package com.example.student.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.student.entity.Course;
import com.example.student.repository.CourseRepository;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
