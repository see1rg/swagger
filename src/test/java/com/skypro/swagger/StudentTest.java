package com.skypro.swagger;


import com.skypro.swagger.models.Faculty;
import com.skypro.swagger.models.Student;
import com.skypro.swagger.repository.FacultyRepository;
import com.skypro.swagger.repository.StudentRepository;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @BeforeEach
    public void resetRepository() {
        studentRepository.deleteAll();
    }

    @Test
    void getStudentWithAgeEqualsTest() throws Exception {
        int age = createOneStudent("Bob", 22).getAge();
        createOneStudent("Den", 32);
        ResponseEntity<Student[]> response = restTemplate.exchange("http://localhost:" + port + "/students/find/{age}",
                HttpMethod.GET, null, Student[].class, age);
        Student[] students = response.getBody();
        Student student = students[0];
        MatcherAssert.assertThat(student.getAge(), is(22));
    }

    @Test
    void getFacultyByStudentTest() throws Exception {
        Faculty nameFaculty = createOneFaculty("hufflepuff", "yellow");
        Student student = createOneStudent("Nick", 33, nameFaculty);
        student.setFaculty(nameFaculty);

        ResponseEntity<Faculty[]> response = restTemplate.exchange("/students/find-faculty/{name}", HttpMethod.GET,
                null, Faculty[].class, student.getName());
        Faculty faculty = response.getBody()[0];
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(faculty, is(nameFaculty));
    }

    @Test
    void deleteStudentTest() throws Exception {
        long id = createOneStudent("Nick", 33).getId();
        ResponseEntity<Student> response = restTemplate.exchange("/students/{id}", HttpMethod.DELETE,
                null, Student.class, id);
        Assertions.assertNull(response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void getAllStudentTest() throws Exception {
        createOneStudent("Bob", 22);
        createOneStudent("Den", 32);

        ResponseEntity<List<Student>> response = restTemplate.exchange("/students", HttpMethod.GET, //exchange - Этот метод принимает два параметра. Сущность запроса и тип ответа. Сущность запроса - это подготовленный запрос в виде класса RequestEntity. Например RequestEntity.post(...)
                null, new ParameterizedTypeReference<List<Student>>() {
                }); // parameter для задания типа ответа с помощью дженериков
        List<Student> students = response.getBody();

        assertThat(students, hasSize(2));
        assertThat(students.get(1).getName(), is("Den"));
    }

    @Test
    void createStudentTest() throws Exception {
        Student student = new Student("Bob", 22);

        ResponseEntity<Student> response = restTemplate.postForEntity("/students", student, Student.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getId(), notNullValue());
        MatcherAssert.assertThat(response.getBody().getName(), is("Bob"));
        MatcherAssert.assertThat(response.getBody().getAge(), is(22));
    }

    @Test
    void editStudentTest() throws Exception {
        long id = createOneStudent("Nick", 51).getId();
        Student student = new Student("Michail", 19);
        HttpEntity<Student> entity = new HttpEntity<Student>(student);

        ResponseEntity<Student> response = restTemplate.exchange("/students/{id}", HttpMethod.PUT, entity, Student.class,
                id);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getId(), notNullValue());
        assertThat(response.getBody().getName(), is("Michail"));
    }

    private Student createOneStudent(String name, int age) {
        Student student = new Student(name, age);
        return studentRepository.save(student);
    }

    private Student createOneStudent(String name, int age, Faculty faculty) {
        Student student = new Student(name, age, faculty);
        return studentRepository.save(student);
    }

    private Faculty createOneFaculty(String name, String color) {
        Faculty faculty = new Faculty(name, color);
        return facultyRepository.save(faculty);
    }
}