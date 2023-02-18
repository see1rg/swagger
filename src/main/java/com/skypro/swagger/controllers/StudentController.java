package com.skypro.swagger.controllers;

import com.skypro.swagger.models.Avatar;
import com.skypro.swagger.models.Student;
import com.skypro.swagger.services.AvatarService;
import com.skypro.swagger.services.StudentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }




    @GetMapping("/number-of-all")
    public ResponseEntity numberOfAllStudents() {
        return ok(studentService.numberOfAllStudents());
    }

    @GetMapping("/avg-age-of-all")
    public ResponseEntity avgAgeOfAllStudents() {
        return ok(studentService.avgAgeOfAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ok(student);
    }

    @GetMapping("/find/{age}")
    public ResponseEntity<List<Student>> getStudentWithAgeEquals(@PathVariable int age,
                                                                 @RequestParam(required = false) Integer max) {

        if (age > 0 && max == null) {
            return ok(studentService.findStudentWithAge(age));
        }
        if (age > 0 && max > 0) {
            return ok(studentService.findStudentByAgeBetween(age, max));

        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/find-faculty/{name}")
    public ResponseEntity getFacultyByStudent(@PathVariable String name) {
        if (name != null) {
            return ok(studentService.findFacultyByStudents(name));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudent() {
        return ok(studentService.getAllStudent());
    }

    @GetMapping("/last-five")
    public ResponseEntity<Collection<Student>> getLastFiveStudents() {
        return ok(studentService.getLastFiveStudents());
    }

    @GetMapping("/start-with-big-a")
    public ResponseEntity<Collection<String>> getStudentStartWith() {
        return ok(studentService.findStudentsStartWithA());
    }


    @PostMapping
    public ResponseEntity createStudent(@RequestBody Student student) {
        return ok(studentService.createStudent(student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student findStudent = studentService.editStudent(student);
        if (findStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ok(findStudent);
    }

    @PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadCover(@PathVariable Long id,
                                              @RequestParam MultipartFile cover) throws IOException {
        if (cover.getSize() >= 1024 * 300) {
            return ResponseEntity.badRequest().body("File is too big.");
        }
        avatarService.uploadAvatar(id, cover);
        return ok().build();
    }

    @GetMapping(value = "{id}/avatar-from-db")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        Avatar avatar = avatarService.findAvatarByStudentId(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getPreview().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getPreview());
    }

    @GetMapping(value = "/{id}/avatar-from-file")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatarByStudentId(id);

        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping(value = "/avatars")
    public ResponseEntity<List<Avatar>> getAllAvatar(@RequestParam("page") Integer pageNumber,
                                                     @RequestParam("size") Integer pageSize) {
        List<Avatar> avatars = avatarService.getAllAvatar(pageNumber, pageSize);
        avatars.forEach(avatar -> avatar.setPreview(null));
        return ok(avatars);
    }

}
