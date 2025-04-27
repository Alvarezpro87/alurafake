package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.*;
import br.com.alura.AluraFake.task.dto.NewSingleChoiceTaskDTO;
import br.com.alura.AluraFake.task.dto.OptionDTO;
import br.com.alura.AluraFake.task.repository.TaskRepository;
import br.com.alura.AluraFake.task.service.TaskService;
import br.com.alura.AluraFake.user.Role;
import br.com.alura.AluraFake.user.User;
import br.com.alura.AluraFake.util.BusinessException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Test
    void singleChoiceComDuasCorretasDeveFalhar() {

        TaskRepository taskRepo   = mock(TaskRepository.class);
        CourseRepository courseRepo = mock(CourseRepository.class);


        User instructor = new User("Instructor", "inst@fake.com", Role.INSTRUCTOR);
        Course course  = new Course("Título", "Desc", instructor);
        when(courseRepo.findById(1L)).thenReturn(java.util.Optional.of(course));

        TaskService service = new TaskService(taskRepo, courseRepo);


        NewSingleChoiceTaskDTO dto = new NewSingleChoiceTaskDTO(
                1L, "Statement", 1,
                List.of(
                        new OptionDTO("Java",  true),
                        new OptionDTO("Ruby",  true)
                )
        );


        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.createSingleChoice(dto));

        assertEquals("Single choice must have exactly 1 correct option", ex.getMessage());
    }
}
