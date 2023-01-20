package com.skypro.swagger.controllers;

import com.skypro.swagger.models.Faculty;
import com.skypro.swagger.models.Student;
import com.skypro.swagger.repository.StudentRepository;
import com.skypro.swagger.services.FacultyService;
import com.skypro.swagger.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;
    private final StudentService studentService;
    private final StudentRepository studentRepository;

    @Autowired
    public FacultyController(FacultyService facultyService,
                             StudentService studentService,
                             StudentRepository studentRepository) {
        this.facultyService = facultyService;
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable("id") long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAllFaculty() {
        return ResponseEntity.ok(facultyService.getAllFaculty());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity createFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.createFaculty(faculty));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty findFaculty = facultyService.editFaculty(faculty);
        if (findFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findFaculty);
    }

    @GetMapping("/get/{color}")
    public ResponseEntity<List<Faculty>> getFacultyWithColorEquals(@RequestParam(required = false) String color,
                                                                   @RequestParam(required = false) String name) {
        if (color != null || name != null) {
            return ResponseEntity.ok(facultyService.findByColorOrNameIgnoreCase(color, name));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<List<Student>> getAllStudentFaculty(@PathVariable long id) {
        if (id > -1) {
            return ResponseEntity.ok(studentService.findStudentsByFaculty(id));
        }
        return ResponseEntity.notFound().build();
    }
}
