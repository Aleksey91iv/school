package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import java.util.Collection;

@RestController
@RequestMapping("students")
public class StudentController {

    @Autowired
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student returnedStudent = studentService.createStudent(student);
        if (returnedStudent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(returnedStudent);
    }

    @GetMapping
    public Collection<Student> getAllStudents()
    {
        return studentService.getAllStudents();
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id)
    {
        Student returnedStudent = studentService.getStudentById(id);
        if (returnedStudent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(returnedStudent);
    }

    @GetMapping("/faculty/{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable Long id)
    {
        Faculty returnedFaculty = studentService.getFaculty(id);
        if (returnedFaculty == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(returnedFaculty);
    }

    @GetMapping("/byAge/{age}")
    public ResponseEntity<Collection<Student>> getStudentByAge(@PathVariable int age) {
        if (age < 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(studentService.getStudentByAge(age));
    }

    @GetMapping("/betweenage")
    public ResponseEntity<Collection<Student>> getStudentByBetweenAge(@RequestParam int minAge, @RequestParam int maxAge) {
        if (minAge < 0 || maxAge < 0 || maxAge < minAge) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(studentService.getAllStudentBetweenAge(minAge, maxAge));
    }

    @PutMapping("/put")
    public ResponseEntity<Student> putStudent(@RequestBody Student student, @RequestParam long id) {
        Student returnedStudent = studentService.editStudent(student, id);
        if (returnedStudent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(returnedStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}
