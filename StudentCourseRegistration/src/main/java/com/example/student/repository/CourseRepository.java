
package com.example.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.student.entity.Course;

public interface CourseRepository
        extends JpaRepository<Course, Integer> {
}
