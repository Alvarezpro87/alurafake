package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(
        name = "tasks",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"course_id", "statement"}
        )
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Task implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 4, max = 255)
    @Column(nullable = false, length = 255)
    private String statement;

    @Positive
    @Column(name = "`order`", nullable = false)
    private Integer order;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Type type;


    protected Task() { }
    protected Task(String st, Integer order, Course c, Type t) {
        this.statement = st;
        this.order     = order;
        this.course    = c;
        this.type      = t;
    }

}