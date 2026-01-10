package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultiesRepository;

import java.util.Collection;

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

    public Collection<Faculty> getFacultyByColor(String color) {
        return facultiesRepository.findAllByColor(color);
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
