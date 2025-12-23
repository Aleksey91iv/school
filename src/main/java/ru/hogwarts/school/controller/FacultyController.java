package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculties")
public class FacultyController {

    @Autowired
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty returnedFaculty = facultyService.createFaculty(faculty);
        if (returnedFaculty == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(returnedFaculty);
    }

    @GetMapping
    public Collection<Faculty> getAllFaculties()
    {
        return facultyService.getAllFaculties();
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable long id)
    {
        Faculty returnedFaculty = facultyService.getFacultyById(id);
        if (returnedFaculty == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(returnedFaculty);
    }

    @GetMapping("/byColor/{color}")
    public Collection<Faculty> getFacultyByColor(@PathVariable String color) {
        return facultyService.getFacultyByColor(color);
    }

    @PutMapping
    public ResponseEntity<Faculty> putFaculty(@RequestBody Faculty faculty) {
        Faculty returnedFaculty = facultyService.editFaculty(faculty);
        if (returnedFaculty == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(returnedFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        Faculty returnedFaculty = facultyService.deleteFaculty(id);
        if (returnedFaculty == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(returnedFaculty);
    }
}
