package com.skypro.swagger.services;

import com.skypro.swagger.models.Faculty;
import com.skypro.swagger.repository.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Requesting to create the faculty: {}.", faculty);
        return facultyRepository.save(faculty);
    }


    public Faculty findFaculty(long id) {
        logger.info("Requesting to find the faculty with id: {}.", id);
        return facultyRepository.findById(id).orElseThrow();
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.info("Requesting to edit the faculty: {}.", faculty);
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.info("Requesting to delete the faculty with id: {}.", id);
        facultyRepository.deleteById(id);
    }

    public List<Faculty> getAllFaculty() {
        logger.info("Requesting all faculties.");
        return facultyRepository.findAll();
    }


    public List<Faculty> findByColorOrNameIgnoreCase(String text) {
        logger.info("Requesting to find the faculty by the color: {} or name: {}.", text, text);
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(text, text);
    }

    public String getLongestFaculty() {
        logger.info("Requesting longest faculty.");
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElseThrow();
    }
}
