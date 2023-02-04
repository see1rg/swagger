package com.skypro.swagger.controllers;

import com.skypro.swagger.services.AvatarService;
import com.skypro.swagger.services.StudentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/avatars")
public class AvatarController {
    private final StudentService studentService;
    private final AvatarService avatarService;

    public AvatarController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }
}
