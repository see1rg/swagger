package com.skypro.swagger.services;

import com.skypro.swagger.models.Faculty;
import com.skypro.swagger.models.Student;
import com.skypro.swagger.repository.FacultyRepository;
import com.skypro.swagger.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepository,
                          FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }


    public Student findStudent(long id) {
        return studentRepository.findById(id).get();
    }

    public List<Student> findStudentWithAge(int age) {
        return studentRepository.findByAge(age);
    }

    public List<Student> findStudentsByFaculty(long id) {
        return studentRepository.findStudentsByFaculty_Id(id);
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public List<Student> findStudentByAgeBetween(int age, Integer max) {
        return studentRepository.findByAgeBetween(age, max);
    }

    public List<Faculty> findFacultyByStudents(String name) {
        return facultyRepository.findFacultyByStudentsName(name);
    }
}
