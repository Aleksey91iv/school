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
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FacultyController facultyController;

    @Test
    void controllerIsInitializeSuccess() {
        Assertions.assertNotNull(facultyController);
        ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port +"/faculties", String.class);
    }

    @Test
    void addFacultyAndPutRequests() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("TestAddFaculty");
        faculty.setColor("TestBlackColor");

        Faculty postResponse = this.restTemplate.postForObject("http://localhost:" + port +"/faculties", faculty, Faculty.class);

        Assertions.assertNotNull(postResponse);
        Assertions.assertTrue(postResponse.getName().equals(faculty.getName()));
        Assertions.assertTrue(postResponse.getColor().equals(faculty.getColor()));

        faculty.setName("TestPutFaculty");
        faculty.setColor("TestGreenColor");
        faculty.setStudents(Collections.emptySet());

        this.restTemplate.put(URI.create("http://localhost:" + port +"/faculties?id=" + postResponse.getId()), faculty);
        Faculty putTest = facultyController.getFacultyById(postResponse.getId()).getBody();

        facultyController.deleteFaculty(postResponse.getId());

        Assertions.assertNotNull(putTest);
        Assertions.assertTrue(putTest.getName().equals(faculty.getName()));
        Assertions.assertTrue(putTest.getColor().equals(faculty.getColor()));
        Assertions.assertEquals(putTest.getId(), postResponse.getId());
    }

    @Test
    void isNotEmptyGetAllStudentsList() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("TestPost");
        faculty.setColor("TestColor");
        faculty.setStudents(Collections.emptySet());

        Faculty postResponse = this.restTemplate.postForObject("http://localhost:" + port +"/faculties", faculty, Faculty.class);

        Assertions.assertNotNull(postResponse);
        Assertions.assertTrue(postResponse.getName().equals(faculty.getName()));
        Assertions.assertTrue(postResponse.getColor().equals(faculty.getColor()));

        Collection<Faculty> response = this.restTemplate.getForObject("http://localhost:" + port +"/faculties", Collection.class);

        facultyController.deleteFaculty(postResponse.getId());

        Assertions.assertTrue(response != null && !response.isEmpty());
    }

    @Test
    void IsFoundByColor() throws Exception {
        String testName = "TestAddFaculty";
        String testColor = "TestBlackColor";
        Faculty faculty = new Faculty();
        faculty.setName(testName);
        faculty.setColor(testColor);

        Faculty postResponse = this.restTemplate.postForObject("http://localhost:" + port +"/faculties", faculty, Faculty.class);

        Assertions.assertNotNull(postResponse);
        Assertions.assertTrue(postResponse.getName().equals(faculty.getName()));
        Assertions.assertTrue(postResponse.getColor().equals(faculty.getColor()));

        ResponseEntity<Collection<Faculty>> response = this.restTemplate
            .exchange("http://localhost:" + port +"/faculties/byColor/" + testColor,
            HttpMethod.GET, null, new ParameterizedTypeReference<Collection<Faculty>>() { });

        facultyController.deleteFaculty(postResponse.getId());

        Assertions.assertTrue(response.getBody() != null);
        Assertions.assertFalse(response.getBody().isEmpty());
        Assertions.assertTrue(response.getBody().contains(postResponse));
    }

    @Test
    void isNotFoundStudentGetByIdRequest() throws Exception {

        ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port +"/faculties/byId/-1", String.class);
        Assertions.assertTrue(response.getStatusCode() == HttpStatusCode.valueOf(500));
    }
}
