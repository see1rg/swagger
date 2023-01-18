package com.skypro.swagger.repository;

import com.skypro.swagger.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Integer> {
    List<Student> findByAge(int age);
}
