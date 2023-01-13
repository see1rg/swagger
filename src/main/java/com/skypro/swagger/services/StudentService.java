package com.skypro.swagger.services;

import com.skypro.swagger.models.Student;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;

@Service
public class StudentService {

    private final HashMap<Long, Student> students = new HashMap<>();
    private long lastId = 0;

    public Student createStudent(Student student) {
        student.setId(++lastId);
        students.put(lastId, student);
        return student;
    }


    public Student findStudent(long id) {
        return students.get(id);
    }

    public Student editStudent(Student faculty) {
        if (students.containsKey(faculty.getId())) {
            students.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public Student deleteStudent(long Id) {
        return students.remove(Id);
    }

    public Collection<Student> getAllStudent() {
        return students.values();
    }
}
