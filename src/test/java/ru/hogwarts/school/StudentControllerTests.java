package ru.hogwarts.school;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

import java.net.URI;
import java.util.Collection;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentController studentController;

    @Test
    void controllerIsInitializeSuccess() {
        Assertions.assertNotNull(studentController);
        ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port +"/students", String.class);
    }

    @Test
    void addStudentAndPutRequests() throws Exception {
        Student student = new Student();
        student.setName("TestPost");
        student.setAge(99);
        Student postResponse = this.restTemplate.postForObject("http://localhost:" + port +"/students", student, Student.class);

        Assertions.assertNotNull(postResponse);
        Assertions.assertTrue(postResponse.getName().equals(student.getName()));
        Assertions.assertSame(postResponse.getAge(), student.getAge());

        student.setName("TestPut");
        student.setAge(100);

        this.restTemplate.put(URI.create("http://localhost:" + port +"/students/put?id=" + postResponse.getId()), student);
        Student putTest = studentController.getStudentById(postResponse.getId()).getBody();

        studentController.deleteStudent(postResponse.getId());

        Assertions.assertNotNull(putTest);
        Assertions.assertTrue(putTest.getName().equals(student.getName()));
        Assertions.assertSame(putTest.getAge(), student.getAge());
        Assertions.assertSame(putTest.getId(), postResponse.getId());
    }

    @Test
    void isNotEmptyGetAllStudentsList() throws Exception {
        Student student = new Student();
        student.setName("TestPost");
        student.setAge(99);

        Student postResponse = this.restTemplate.postForObject("http://localhost:" + port +"/students", student, Student.class);

        Assertions.assertNotNull(postResponse);
        Assertions.assertTrue(postResponse.getName().equals(student.getName()));
        Assertions.assertSame(postResponse.getAge(), student.getAge());

        Collection<Student> response = this.restTemplate.getForObject("http://localhost:" + port +"/students", Collection.class);

        studentController.deleteStudent(postResponse.getId());

        Assertions.assertTrue(response != null && !response.isEmpty());
    }

    @Test
    void getByAgeAndByBetweenAgeStudentAndPutRequests() throws Exception {
        Student student1 = new Student();
        student1.setName("TestOne");
        student1.setAge(100);

        Student student2 = new Student();
        student2.setName("TestTwo");
        student2.setAge(101);

        Student postResponse1 = this.restTemplate
            .postForObject("http://localhost:" + port +"/students", student1, Student.class);
        Student postResponse2 = this.restTemplate
            .postForObject("http://localhost:" + port +"/students", student2, Student.class);

        Assertions.assertTrue(postResponse1 != null && postResponse2 != null);
        Assertions.assertTrue(postResponse1.getName().equals(student1.getName()));
        Assertions.assertTrue(postResponse1.getAge() == student1.getAge());
        Assertions.assertTrue(postResponse2.getName().equals(student2.getName()));
        Assertions.assertTrue(postResponse2.getAge() == student2.getAge());

        ResponseEntity<Collection<Student>> byAgeResponse = this.restTemplate
            .exchange("http://localhost:" + port +"/students/byAge/" + postResponse1.getAge(),
                HttpMethod.GET, null, new ParameterizedTypeReference<Collection<Student>>() { });

        ResponseEntity<Collection<Student>> byBeetwenAgeResponse = this.restTemplate
            .exchange("http://localhost:" + port +"/students/betweenage?minAge=" + postResponse1.getAge()
                + "&maxAge=" + postResponse2.getAge(),
                HttpMethod.GET, null, new ParameterizedTypeReference<Collection<Student>>() { });

        Assertions.assertNotNull(byAgeResponse.getBody());
        Assertions.assertTrue(!byAgeResponse.getBody().isEmpty());
        Assertions.assertTrue(byAgeResponse.getBody().stream()
            .anyMatch(s -> s.equals(postResponse1)));

        Assertions.assertNotNull(byBeetwenAgeResponse.getBody());
        Assertions.assertTrue(!byBeetwenAgeResponse.getBody().isEmpty());
        Assertions.assertTrue(byBeetwenAgeResponse.getBody().stream()
                .anyMatch(s -> s.equals(postResponse1)));
        Assertions.assertTrue(byBeetwenAgeResponse.getBody().stream()
                .anyMatch(s -> s.equals(postResponse2)));

        studentController.deleteStudent(postResponse1.getId());
        studentController.deleteStudent(postResponse2.getId());
    }

    @Test
    void isNotFoundStudentGetByIdRequest() throws Exception {

        ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port +"/students/byId/-1", String.class);
        Assertions.assertTrue(response.getStatusCode() == HttpStatusCode.valueOf(500));
    }
}
