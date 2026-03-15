package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentsRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class StudentService {

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

    public Student editStudent(Student student, long id) {
        Student updatingStudent = studentsRepository.getById(id);
        updatingStudent.setName(student.getName());
        updatingStudent.setAge(student.getAge());
        return studentsRepository.save(updatingStudent);
    }

    public void deleteStudent(Long id) {
        studentsRepository.deleteById(id);
    }
}
