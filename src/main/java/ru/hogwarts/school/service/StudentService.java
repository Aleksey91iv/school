package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final Map<Long,Student> students;
    private long idCounter;

    public StudentService() {
        this.students = new HashMap<>();
    }

    public Student createStudent(Student student)
    {
        if (student.getName().isBlank()) {
            return null;
        }
        student.setId(++idCounter);
        students.put(idCounter, student);
        return student;
    }

    public Student getStudentById(Long id) {
        if (students.containsKey(id)) {
            return students.get(id);
        }
        return null;
    }

    public Collection<Student> getAllStudents() {
        return students.values();
    }

    public Collection<Student> getStudentByAge(int age) {
        return students.values()
            .stream()
            .filter(s -> s.getAge() == age)
            .collect(Collectors.toSet());
    }

    public Student editStudent(Student student) {
        if (students.containsKey(student.getId())) {
            students.put(student.getId(), student);
            return student;
        }
        return null;
    }

    public Student deleteStudent(Long id) {
        return students.remove(id);
    }
}
