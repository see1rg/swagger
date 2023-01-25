package com.skypro.swagger.repository;

import com.skypro.swagger.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int min, int max);

    List<Student> findStudentsByFaculty_Id(long id);


    Optional<Student> findById(Long studentId);
}

