package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService
{
    private final Map<Long, Faculty> faculties;
    private long idCounter;

    public FacultyService() {
        this.faculties = new HashMap<Long, Faculty>();
    }

    public Faculty createFaculty(Faculty faculty) {
        if (faculty.getName().isBlank()) {
            return null;
        }
        faculty.setId(++idCounter);
        faculties.put(idCounter, faculty);
        return faculty;
    }

    public Faculty getFacultyById(Long id) {
        if (faculties.containsKey(id)) {
            return faculties.get(id);
        }
        return null;
    }

    public Collection<Faculty> getFacultyByColor(String color) {
        return faculties.values()
            .stream()
            .filter(f -> f.getColor().equals(color))
            .collect(Collectors.toSet());
    }

    public Collection<Faculty> getAllFaculties() {
        return faculties.values();
    }

    public Faculty editFaculty(Faculty faculty) {
        if (faculties.containsKey(faculty.getId())) {
            faculties.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public Faculty deleteFaculty(Long id) {
        return faculties.remove(id);
    }
}
