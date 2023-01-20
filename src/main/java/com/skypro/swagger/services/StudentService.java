package com.skypro.swagger.services;

import com.skypro.swagger.models.Student;
import com.skypro.swagger.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private int lastId = 0;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }


    public Student findStudent(int id) {
        return studentRepository.findById(id).get();
    }

    public List<Student> findStudentWithAge(int age) {
        return studentRepository.findByAge(age);
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(int id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudent() {
        return studentRepository.findAll();
    }
}
