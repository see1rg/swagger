package com.skypro.swagger.services;

import com.skypro.swagger.models.Faculty;
import com.skypro.swagger.repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }


    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public List<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }


    public List<Faculty> findByColorOrNameIgnoreCase(String color,String name) {
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }
}
