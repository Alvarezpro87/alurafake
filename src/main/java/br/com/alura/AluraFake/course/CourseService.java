package br.com.alura.AluraFake.course;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {

    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void publishCourse(Long id) {
        Course course = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        if (course.getStatus() != Status.BUILDING) {
            throw new IllegalStateException("Course must be in BUILDING status to be published");
        }


    }
}