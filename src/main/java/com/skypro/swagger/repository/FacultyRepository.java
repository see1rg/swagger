package com.skypro.swagger.repository;

import com.skypro.swagger.models.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByColorIgnoreCaseOrNameIgnoreCase(String color, String name);

    List<Faculty> findFacultyByStudentsId(long id);

    List<Faculty> findFacultyByStudentsName(String name);

    List<Faculty> findAll();
}
