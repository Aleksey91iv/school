package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentsRepository;

import java.util.Collection;
import java.util.Optional;

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
        return studentsRepository.findAllByAge(age);
    }

    public Collection<Student> getAllStudentBetweenAge(int minAge, int maxAge) {
        return studentsRepository.findAllByAgeBetween(minAge,maxAge);
    }

    public Faculty getFaculty(Long id) {
        Optional<Student> student = studentsRepository.findById(id);
        if (student.isPresent()) {
            return student.get().getFaculty();
        }
        return null;
    }

    public Student editStudent(Student student) {
        return studentsRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentsRepository.deleteById(id);
    }
}
