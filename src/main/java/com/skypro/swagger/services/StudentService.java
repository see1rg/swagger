package com.skypro.swagger.services;

import com.skypro.swagger.models.Student;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final HashMap<Integer, Student> students = new HashMap<>();
    private int lastId = 0;

    public Student createStudent(Student student) {
        student.setId(++lastId);
        students.put(lastId, student);
        return student;
    }


    public Student findStudent(int id) {
        return students.get(id);
    }

    public List<Student> findStudentWithAge(int age){
        return students.values().stream().filter(student -> student.getAge() == age).collect(Collectors.toList());
    }

    public Student editStudent(Student faculty) {
        if (students.containsKey(faculty.getId())) {
            students.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public Student deleteStudent(int id) {
        return students.remove(id);
    }

    public Collection<Student> getAllStudent() {
        return students.values();
    }
}
