package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/** Atividade de resposta aberta – sem opções. */
@Entity
@DiscriminatorValue("OPEN_TEXT")
public class OpenTextTask extends Task {

    protected OpenTextTask() { }  // JPA

    public OpenTextTask(String st, Integer order, Course c) {
        super(st, order, c, Type.OPEN_TEXT);
    }
}
