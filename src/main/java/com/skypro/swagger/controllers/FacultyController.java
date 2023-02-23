package com.skypro.swagger.controllers;

import com.skypro.swagger.models.Faculty;
import com.skypro.swagger.models.Student;
import com.skypro.swagger.repository.StudentRepository;
import com.skypro.swagger.services.FacultyService;
import com.skypro.swagger.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/faculties")
public class FacultyController {
    private final FacultyService facultyService;
    private final StudentService studentService;

    @Autowired
    public FacultyController(FacultyService facultyService,
                             StudentService studentService) {
        this.facultyService = facultyService;
        this.studentService = studentService;
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
    public ResponseEntity<List<Faculty>> getAllFaculty() {
        return ResponseEntity.ok(facultyService.getAllFaculty());
    }

    @GetMapping("/longest-faculty")
    public ResponseEntity<?> longestFaculty() {
        return ResponseEntity.ok(facultyService.getLongestFaculty());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
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

    @GetMapping("/find/{text}")
    public ResponseEntity<List<Faculty>> getFacultyWithColorOrNameEquals(@RequestParam(required = false) String text) {
        return ResponseEntity.ok(facultyService.findByColorOrNameIgnoreCase(text));
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<List<Student>> getAllStudentFaculty(@PathVariable long id) {
        if (id > -1) {
            return ResponseEntity.ok(studentService.findStudentsByFaculty(id));
        }
        return ResponseEntity.notFound().build();
    }
}
