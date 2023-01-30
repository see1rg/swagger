package com.skypro.swagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.swagger.controllers.FacultyController;
import com.skypro.swagger.models.Faculty;
import com.skypro.swagger.repository.FacultyRepository;
import com.skypro.swagger.services.FacultyService;
import com.skypro.swagger.services.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class RestApplicationTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private FacultyService facultyService;
    @SpyBean
    private StudentService studentService;
    @InjectMocks
    FacultyController facultyController;

    @Test
    void getFacultyInfo() throws Exception{
    }

    @Test
    void getAllFaculty() throws Exception{
    }

    @Test
    void deleteFaculty() throws Exception{
    }

    @Test
    void createFaculty() throws Exception{
        Faculty faculty = new Faculty("Raven", "Blue");
        when(facultyRepository.save(faculty)).thenReturn(faculty);

        mvc.perform(post("/faculty")
                .content(new ObjectMapper.writeValueAsString(faculty))

    }

    @Test
    void editFaculty() throws Exception{
    }

    @Test
    void getFacultyWithColorEquals() throws Exception{
    }

    @Test
    void getAllStudentFaculty() throws Exception{
    }





}
