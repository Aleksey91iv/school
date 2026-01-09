package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentsRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private final StudentsRepository studentsRepository;

    public StudentService(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    public Student createStudent(Student student)
    {
        return studentsRepository.save(student);
    }

    public Student getStudentById(Long id) {
        return studentsRepository.findById(id).get();
    }

    public Collection<Student> getAllStudents() {
        return studentsRepository.findAll();
    }

    public Collection<Student> getStudentByAge(int age) {
        return studentsRepository.findAll()
            .stream()
            .filter(s -> s.getAge() == age)
            .collect(Collectors.toSet());
    }

    public Student editStudent(Student student) {
        return studentsRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentsRepository.deleteById(id);
    }
}
