package com.skypro.swagger.repository;

import com.skypro.swagger.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int min, int max);

    List<Student> findStudentsByFaculty_Id(long id);


}
