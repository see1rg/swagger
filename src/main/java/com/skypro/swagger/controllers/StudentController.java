package com.skypro.swagger.controllers;

import com.skypro.swagger.models.Student;
import com.skypro.swagger.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable int id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("{age}")
    public List<Student> getStudentWithAgeEquals(@PathVariable int age){
        if (age > 0) {
            return studentService.findStudentWithAge(age);}
        return Collections.emptyList();
    }

    @DeleteMapping("{id}")
    public Student deleteStudent(@PathVariable int id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudent() {
        return ResponseEntity.ok(studentService.getAllStudent());
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }


    @PutMapping("{id}")
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student findStudent = studentService.editStudent(student);
        if (findStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findStudent);
    }
}
