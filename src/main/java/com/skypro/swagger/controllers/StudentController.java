package com.skypro.swagger.controllers;

import com.skypro.swagger.models.Student;
import com.skypro.swagger.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("/getage/{age}")
    public ResponseEntity<List<Student>> getStudentWithAgeEquals(@PathVariable int age,
                                                                 @RequestParam(required = false) Integer max) {

        if (age > 0 && max == null) {
            System.out.println(age);
            return ResponseEntity.ok(studentService.findStudentWithAge(age));
        }
        if (age > 0 && max > 0) {
            return ResponseEntity.ok(studentService.findStudentByAgeBetween(age, max));

        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getfaculty/{name}")
    public ResponseEntity getFacultyByStudent(@PathVariable String name) {
        if (name != null) {
            return ResponseEntity.ok(studentService.findFacultyByStudents(name));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudent() {
        return ResponseEntity.ok(studentService.getAllStudent());
    }

    @PostMapping
    public ResponseEntity createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student findStudent = studentService.editStudent(student);
        if (findStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findStudent);
    }
}
