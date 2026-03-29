package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentsRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentsRepository studentsRepository;

    @SpyBean
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void createAndGetByIdStudentTest() throws Exception {
        Long testId = 1L;
        String testName = "TestName";
        int testAge = 12;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", testName);
        studentObject.put("age", testAge);

        Student student = new Student();
        student.setId(testId);
        student.setName(testName);
        student.setAge(testAge);

        when(studentsRepository.save(any(Student.class))).thenReturn(student);
        when(studentsRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
            .post("/students")
            .content(studentObject.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(testId))
            .andExpect(jsonPath("$.name").value(testName))
            .andExpect(jsonPath("$.age").value(testAge));

        mockMvc.perform(MockMvcRequestBuilders
            .get("/students/byId/1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(testId))
            .andExpect(jsonPath("$.name").value(testName))
            .andExpect(jsonPath("$.age").value(testAge));
    }

    @Test
    public void findAllStudentsIsEmpty() throws Exception {
        Collection<Student> empty = Collections.<Student>emptyList();
        when(studentsRepository
            .findAll())
            .thenReturn(Collections.<Student>emptyList());

        JSONObject studentObject = new JSONObject();
        for (int i = 0; i < empty.size(); i++) {
            studentObject.put("", empty);
        }

        mockMvc.perform(MockMvcRequestBuilders
            .get("/students")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void findAllByBetweenAgeIsEmpty() throws Exception {
        Collection<Student> empty = Collections.<Student>emptyList();
        when(studentsRepository
            .findAllByAgeBetween(3, 5))
            .thenReturn(Collections.<Student>emptyList());

        JSONObject studentObject = new JSONObject();
        for (int i = 0; i < empty.size(); i++) {
            studentObject.put("", empty);
        }

        mockMvc.perform(MockMvcRequestBuilders
            .get("/students/betweenage?minAge=3&maxAge=5")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void findAllByAgeIsEmpty() throws Exception {
        Collection<Student> empty = Collections.<Student>emptyList();
        when(studentsRepository
                .findAllByAgeBetween(3, 5))
                .thenReturn(Collections.<Student>emptyList());

        JSONObject studentObject = new JSONObject();
        for (int i = 0; i < empty.size(); i++) {
            studentObject.put("", empty);
        }

        mockMvc.perform(MockMvcRequestBuilders
            .get("/students/byAge/3")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void putStudent() throws Exception {
        Long testId = 1L;
        String testName = "TestName";
        int testAge = 12;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", testName);
        studentObject.put("age", testAge);

        Student student = new Student();
        student.setId(testId);
        student.setName(testName);
        student.setAge(testAge);

        when(studentsRepository.save(any(Student.class))).thenReturn(student);
        when(studentsRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
            .post("/students")
            .content(studentObject.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(testId))
            .andExpect(jsonPath("$.name").value(testName))
            .andExpect(jsonPath("$.age").value(testAge));

        mockMvc.perform(MockMvcRequestBuilders
            .get("/students/byId/1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(testId))
            .andExpect(jsonPath("$.name").value(testName))
            .andExpect(jsonPath("$.age").value(testAge));

        int newTestAge = 15;
        JSONObject newStudentObject = new JSONObject();
        newStudentObject.put("id", testId);
        newStudentObject.put("name", testName);
        newStudentObject.put("age", newTestAge);
        student.setAge(newTestAge);

        mockMvc.perform(MockMvcRequestBuilders
            .put("/students")
            .content(newStudentObject.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(testId))
            .andExpect(jsonPath("$.name").value(testName))
            .andExpect(jsonPath("$.age").value(newTestAge));
    }
}
