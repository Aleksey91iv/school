package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultiesRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
public class FacultyService
{
    private final FacultiesRepository facultiesRepository;

    public FacultyService(FacultiesRepository facultiesRepository) {
        this.facultiesRepository = facultiesRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultiesRepository.save(faculty);
    }

    public Faculty getFacultyById(Long id) {
        return facultiesRepository.findById(id).get();
    }

    public Collection<Faculty> getFacultiesByColor(String color) {
        return facultiesRepository.findAllByColor(color);
    }

    public Collection<Student> getStudentsById(Long id) {
        Optional<Faculty> faculty = facultiesRepository.findById(id);
        if (faculty.isPresent()) {
            return faculty.get().getStudents();
        }
        return null;
    }

    public Collection<Faculty> getAllFaculties() {
        return facultiesRepository.findAll();
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultiesRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        facultiesRepository.deleteById(id);
    }
}
