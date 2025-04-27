package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("MULTIPLE_CHOICE")
public class MultipleChoiceTask extends Task {

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();

    protected MultipleChoiceTask() { }

    public MultipleChoiceTask(String st, Integer order, Course c) {
        super(st, order, c, Type.MULTIPLE_CHOICE);
    }

    public List<Option> getOptions() { return options; }
}
