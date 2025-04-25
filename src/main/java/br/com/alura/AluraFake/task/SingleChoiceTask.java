package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;



@Entity
@DiscriminatorValue("SINGLE_CHOICE")
public class SingleChoiceTask extends Task {

    // 2–5 opções; validação acontecerá no serviço
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();

    protected SingleChoiceTask() { }

    public SingleChoiceTask(String st, Integer order, Course c) {
        super(st, order, c, Type.SINGLE_CHOICE);
    }

    public List<Option> getOptions() { return options; }
}
