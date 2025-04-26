package br.com.alura.AluraFake.task;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "options")
public class Option {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 4, max = 80)
    @Column(name = "option_text", nullable = false, length = 80)
    private String option;

    private boolean isCorrect;

    @ManyToOne(optional = false)
    private Task task;

    protected Option() { }

    public Option(String option, boolean isCorrect, Task task) {
        this.option    = option;
        this.isCorrect = isCorrect;
        this.task      = task;
    }
}
