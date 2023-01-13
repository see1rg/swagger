package com.skypro.swagger.controllers;

import com.skypro.swagger.models.Faculty;
import com.skypro.swagger.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
@Component
public class FacultyController {
    private final FacultyService facultyService;

    @Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping({"/id"})
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/test")
    public String test() {
        return "mvmvm";

    }

    @PostMapping
    public Faculty createFaculty(@RequestParam("name") String name, @RequestParam("color") String color) {
        return facultyService.createFaculty(name,color);
    }
}

//    @PutMapping()
//    public ResponseEntity<Faculty> editFaculty(@RequestParam ("name") String name, @RequestParam("color") String color){
////    Faculty faculty1 = facultyService.editFaculty(faculty);
////        if (faculty1 == null){
////            return ResponseEntity.notFound().build();
////        }
////        return ResponseEntity.ok(faculty1);
////    }
//return null;}}
