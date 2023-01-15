package com.skypro.swagger.services;

import com.skypro.swagger.models.Faculty;
import com.skypro.swagger.models.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final HashMap<Long, Faculty> faculties = new HashMap<>();
    private long lastId = 0;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++lastId);
        faculties.put(lastId, faculty);
        return faculty;
    }


    public Faculty findFaculty(long id) {
        return faculties.get(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        if (faculties.containsKey(faculty.getId())) {
            faculties.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public Faculty deleteFaculty(long Id) {
        return faculties.remove(Id);
    }

    public Collection<Faculty> getAllFaculty() {
        return faculties.values();
    }


    public List<Faculty> findFacultyWithColor(String color) {
        return faculties.values().stream().filter(faculty -> faculty.getColor()
                .equals(color)).collect(Collectors.toList());
    }
}
