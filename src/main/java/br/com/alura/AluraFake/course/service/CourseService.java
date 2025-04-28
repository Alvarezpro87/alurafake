package br.com.alura.AluraFake.course.service;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.Status;
import br.com.alura.AluraFake.course.repository.CourseRepository;
import br.com.alura.AluraFake.task.MultipleChoiceTask;
import br.com.alura.AluraFake.task.OpenTextTask;
import br.com.alura.AluraFake.task.SingleChoiceTask;
import br.com.alura.AluraFake.task.Task;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


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

        // 1. at least one of each type
        boolean hasOpenText       = course.getTasks().stream().anyMatch(OpenTextTask.class::isInstance);
        boolean hasSingleChoice   = course.getTasks().stream().anyMatch(SingleChoiceTask.class::isInstance);
        boolean hasMultipleChoice = course.getTasks().stream().anyMatch(MultipleChoiceTask.class::isInstance);

        if (!(hasOpenText && hasSingleChoice && hasMultipleChoice)) {
            throw new IllegalStateException("Course must contain at least one task of each type.");
        }

        // 2. orders in continuous sequence (1,2,3,…)
        List<Integer> ordered = course.getTasks().stream()
                .map(Task::getOrder)
                .sorted()
                .toList();

        for (int i = 0; i < ordered.size(); i++) {
            if (ordered.get(i) != i + 1) {
                throw new IllegalStateException("Task order must be continuous (1,2,3…).");
            }
        }

        /* --------------- Publication ---------------- */

        course.setStatus(Status.PUBLISHED);
        course.setPublishedAt(LocalDateTime.now());
        repository.save(course);
    }

    }
