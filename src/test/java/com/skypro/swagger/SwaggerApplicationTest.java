package com.skypro.swagger;


import com.skypro.swagger.models.Student;
import com.skypro.swagger.repository.StudentRepository;
import com.skypro.swagger.services.StudentService;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
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
class SwaggerApplicationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    StudentService studentService;

    @Test
    void getStudentInfo() throws Exception {

    }

    @Test
    void getStudentWithAgeEqualsTest() throws Exception {
        int age = createOneStudent("Bob", 22).getAge();
        createOneStudent("Den", 32);
        System.out.println(studentRepository.findByAge(age));
        Student student  = restTemplate.getForObject("http://localhost:"+ port + "/student/getage/{age}", Student.class,
                age);
        System.out.println(student);
        assertThat(student, is(22));
    }

    @Test
    void getFacultyByStudentTest() throws Exception {
    }

    @Test
    void deleteStudentTest() throws Exception{
        long id = createOneStudent("Nick", 33).getId();
        ResponseEntity<Student> response = restTemplate.exchange("/student/{id}", HttpMethod.DELETE,
                null, Student.class, id);
        Assertions.assertNull(response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void getAllStudentTest() throws Exception{
        createOneStudent("Bob", 22);
        createOneStudent("Den", 32);

        ResponseEntity<List<Student>> response = restTemplate.exchange("/student", HttpMethod.GET, //exchange - Этот метод принимает два параметра. Сущность запроса и тип ответа. Сущность запроса - это подготовленный запрос в виде класса RequestEntity. Например RequestEntity.post(...)
                null, new ParameterizedTypeReference<List<Student>>() {
                }); // parameter для задания типа ответа с помощью дженериков
        List<Student> students = response.getBody();

        assertThat(students, hasSize(2));
        assertThat(students.get(1).getName(), is("Den"));


    }

    @Test
    void createStudentTest() throws Exception{
        Student student = new Student("Bob", 22);

        ResponseEntity<Student> response = restTemplate.postForEntity("/student", student, Student.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getId(), notNullValue());
        MatcherAssert.assertThat(response.getBody().getName(), is("Bob"));
        MatcherAssert.assertThat(response.getBody().getAge(), is(22));
    }

    @Test
    void editStudentTest() throws Exception{
        long id = createOneStudent("Nick", 51).getId();
        Student student = new Student("Michail", 19);
        HttpEntity<Student> entity = new HttpEntity<Student>(student);

        ResponseEntity<Student> response = restTemplate.exchange("/student/{id}", HttpMethod.PUT, entity, Student.class,
                id);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getId(), notNullValue());
        assertThat(response.getBody().getName(), is("Michail"));
    }

    private Student createOneStudent(String name, int age) {
        Student student = new Student(name, age);
        return studentRepository.save(student);
    }
}