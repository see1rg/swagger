package com.skypro.swagger.services;

import com.skypro.swagger.models.Faculty;
import org.springframework.stereotype.Service;

import java.util.HashMap;
@Service
public class FacultyService {

    private final HashMap<Long, Faculty> faculties = new HashMap<>();
    private long lastId = 0;

    public Faculty createFaculty(String name,String color){
        return new Faculty( name, color);
    }


    public Faculty findFaculty( long id){
        return faculties.get(id);
    }

    public Faculty editFaculty(Faculty faculty){
        if (faculties.containsKey(faculty.getId())){
        faculties.put(faculty.getId(), faculty);
        return faculty;}
        return null;
    }

    public Faculty deleteFaculty(long Id){
        return faculties.remove(Id);
    }

}
