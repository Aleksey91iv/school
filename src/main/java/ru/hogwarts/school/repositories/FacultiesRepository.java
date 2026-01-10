package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultiesRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findAllByColor(String color);
}
