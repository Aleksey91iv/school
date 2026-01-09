package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultiesRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FacultyService
{
    @Autowired
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
        return facultiesRepository.findAll()
            .stream()
            .filter(f -> f.getColor().equals(color))
            .collect(Collectors.toSet());
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
