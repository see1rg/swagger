package com.skypro.swagger.services;

import com.skypro.swagger.models.Faculty;
import com.skypro.swagger.models.Student;
import com.skypro.swagger.repository.AvatarRepository;
import com.skypro.swagger.repository.FacultyRepository;
import com.skypro.swagger.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final AvatarRepository avatarRepository;

    public StudentService(StudentRepository studentRepository,
                          FacultyRepository facultyRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.avatarRepository = avatarRepository;
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

    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public List<Student> findStudentByAgeBetween(int age, Integer max) {
        return studentRepository.findByAgeBetween(age, max);
    }

    public List<Faculty> findFacultyByStudents(String name) {
        return facultyRepository.findFacultyByStudentsName(name);
    }

    public Integer numberOfAllStudents() {
        return studentRepository.numberOfAllStudents();
    }

    public Integer avgAgeOfAllStudents(){
        return studentRepository.avgAgeOfAllStudents();
    }

    public List<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }
}
