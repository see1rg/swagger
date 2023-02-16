package com.skypro.swagger.services;

import com.skypro.swagger.models.Faculty;
import com.skypro.swagger.models.Student;
import com.skypro.swagger.repository.AvatarRepository;
import com.skypro.swagger.repository.FacultyRepository;
import com.skypro.swagger.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    Logger logger = LoggerFactory.getLogger(StudentService.class);
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
        logger.info("Requesting to create the student: {}.", student);
        return studentRepository.save(student);
    }


    public Student findStudent(long id) {
        logger.info("Requesting to find the student with id: {}.", id);
        return studentRepository.findById(id).get();
    }

    public List<Student> findStudentWithAge(int age) {
        logger.info("Requesting to find student with age: {}.", age);
        return studentRepository.findByAge(age);
    }

    public List<Student> findStudentsByFaculty(long id) {
        logger.info("Requesting to find the student with the faculty id: {}.", id);
        return studentRepository.findStudentsByFaculty_Id(id);
    }

    public Student editStudent(Student student) {
        logger.info("Requesting to edit the student: {}.", student);
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        logger.info("Requesting to delete the student with id: {}.", id);
        studentRepository.deleteById(id);
    }

    public List<Student> getAllStudent() {
        logger.info("Requesting all students.");
        return studentRepository.findAll();
    }

    public List<Student> findStudentByAgeBetween(int age, Integer max) {
        logger.info("Requesting to find the student by the age between: {} and: {}.", age, max);
        return studentRepository.findByAgeBetween(age, max);
    }

    public List<Faculty> findFacultyByStudents(String name) {
        logger.info("Requesting to find faculty by the name of the student: {}.", name);
        return facultyRepository.findFacultyByStudentsName(name);
    }

    public Integer numberOfAllStudents() {
        logger.info("Requesting the number of all students.");
        return studentRepository.numberOfAllStudents();
    }

    public Integer avgAgeOfAllStudents(){
        logger.info("Requesting average  age of all students.");
        return studentRepository.avgAgeOfAllStudents();
    }

    public List<Student> getLastFiveStudents() {
        logger.info("Requesting to get last five students.");
        return studentRepository.getLastFiveStudents();
    }

    public List<Student> findAll() {
        logger.info("Requesting to get students start with A.");
        return studentRepository.findAll();
    }

    public List<String> findStudentsStartWithA() {
        logger.info("Requesting to get students start with A.");
        return studentRepository.findAll().stream()
                .filter(s -> s.getName().startsWith("A"))
                .sorted(Comparator.comparing(Student::getName))
                .map(s -> s.getName().toUpperCase())
                .collect(Collectors.toList());
    }
}
