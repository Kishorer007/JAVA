
package com.example.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.student.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
