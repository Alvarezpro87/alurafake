package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.course.Status;
import br.com.alura.AluraFake.util.BusinessException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepo;
    private final CourseRepository courseRepo;

    public TaskService(TaskRepository taskRepo, CourseRepository courseRepo) {
        this.taskRepo = taskRepo;
        this.courseRepo = courseRepo;
    }

    /* --------- 1. Open text --------- */
    @Transactional
    public void createOpenText(@Valid NewOpenTextTaskDTO dto) {
        Course c = prepareCourse(dto.courseId());
        validateStatement(c, dto.statement());
        validateSequence(c, dto.order());
        shiftOrders(c, dto.order());

        taskRepo.save(new OpenTextTask(dto.statement(), dto.order(), c));
    }

    /* --------- 2. Single choice --------- */
    @Transactional
    public void createSingleChoice(@Valid NewSingleChoiceTaskDTO dto) {
        Course c = prepareCourse(dto.courseId());
        validateStatement(c, dto.statement());
        validateSequence(c, dto.order());
        validateSingleChoice(dto);
        shiftOrders(c, dto.order());

        SingleChoiceTask task = new SingleChoiceTask(dto.statement(), dto.order(), c);
        dto.options().forEach(o ->
                task.getOptions().add(new Option(o.option(), o.isCorrect(), task))
        );
        taskRepo.save(task);
    }

    /* --------- 3. Multiple choice --------- */
    @Transactional
    public void createMultipleChoice(@Valid NewMultipleChoiceTaskDTO dto) {
        Course c = prepareCourse(dto.courseId());
        validateStatement(c, dto.statement());
        validateSequence(c, dto.order());
        validateMultipleChoice(dto);
        shiftOrders(c, dto.order());

        MultipleChoiceTask task = new MultipleChoiceTask(dto.statement(), dto.order(), c);
        dto.options().forEach(o ->
                task.getOptions().add(new Option(o.option(), o.isCorrect(), task))
        );
        taskRepo.save(task);
    }

    /* ===== Helpers ===== */

    /** brings course and guarantees Status.BUILDING */
    private Course prepareCourse(Long id) {
        Course c = courseRepo.findById(id)
                .orElseThrow(() -> new BusinessException("Course not found"));
        if (c.getStatus() != Status.BUILDING)
            throw new BusinessException("Course is not BUILDING");
        return c;
    }

    private void validateStatement(Course c, String stmt) {
        if (taskRepo.existsByCourseAndStatement(c, stmt))
            throw new BusinessException("Duplicated statement in course");
    }

    /** prevents holes in the sequence (order must be size+1 or smaller) */
    private void validateSequence(Course c, int newOrder) {
        int maxAllowed = taskRepo.findByCourseOrderByOrderAsc(c).size() + 1;
        if (newOrder > maxAllowed)
            throw new BusinessException("Order breaks sequence");
    }

    /** moves orders from the reported position */
    private void shiftOrders(Course c, int newOrder) {
        taskRepo.findByCourseOrderByOrderAsc(c).stream()
                .filter(t -> t.getOrder() >= newOrder)
                .forEach(t -> t.setOrder(t.getOrder() + 1));
    }

    private void validateSingleChoice(NewSingleChoiceTaskDTO dto) {
        long correct = dto.options().stream().filter(OptionDTO::isCorrect).count();
        if (correct != 1)
            throw new BusinessException("Single choice must have exactly 1 correct option");
        commonOptionChecks(dto.statement(), dto.options());
    }

    private void validateMultipleChoice(NewMultipleChoiceTaskDTO dto) {
        long correct   = dto.options().stream().filter(OptionDTO::isCorrect).count();
        long incorrect = dto.options().size() - correct;
        if (correct < 2 || incorrect == 0)
            throw new BusinessException("Multiple choice needs 2+ correct and at least 1 incorrect");
        commonOptionChecks(dto.statement(), dto.options());
    }

    /** options must be unique and different from the statement */
    private void commonOptionChecks(String statement, List<OptionDTO> opts) {
        if (opts.stream().map(OptionDTO::option).distinct().count() != opts.size())
            throw new BusinessException("Options must be unique");
        if (opts.stream().anyMatch(o -> o.option().equalsIgnoreCase(statement)))
            throw new BusinessException("Option cannot equal statement");
    }
}
