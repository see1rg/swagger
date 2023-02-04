package com.skypro.swagger.repository;

import com.skypro.swagger.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int min, int max);

    List<Student> findStudentsByFaculty_Id(long id);

    Optional<Student> findById(Long studentId);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Integer numberOfAllStudents();

    @Query(value = "SELECT AVG(age) from student", nativeQuery = true)
    Integer avgAgeOfAllStudents();
    @Query(value = "SELECT * from student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFiveStudents();
}

