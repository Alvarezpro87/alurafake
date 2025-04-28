package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.course.repository.CourseRepository;
import br.com.alura.AluraFake.course.service.CourseService;
import br.com.alura.AluraFake.user.Role;
import br.com.alura.AluraFake.user.User;
import br.com.alura.AluraFake.user.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;



import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional

class CourseServiceTest {

    @Autowired
    CourseRepository courseRepository;
    @Autowired UserRepository   userRepository;
    @Autowired
    CourseService courseService;

    private User instructor;

    @BeforeEach
    void setUp() {
        instructor = userRepository.save(new User(
                "Inst", "inst@test.com", Role.INSTRUCTOR, "pwd"));
    }

    @Test
    void shouldThrowWhenCourseIsNotBuilding() {
        Course course = new Course("title", "desc", instructor);
        course.setStatus(Status.PUBLISHED);
        courseRepository.save(course);

        assertThrows(IllegalStateException.class,
                () -> courseService.publishCourse(course.getId()));
    }
}
