package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.task.Task;
import br.com.alura.AluraFake.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String title;
    private String description;
    @ManyToOne
    private User instructor;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime publishedAt;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();
    public List<Task> getTasks() { return tasks; }

    @Deprecated
    public Course(){}

    public Course(String title, String description, User instructor) {
        Assert.isTrue(instructor.isInstructor(), "Usuario deve ser um instrutor");
        this.title = title;
        this.instructor = instructor;
        this.description = description;
        this.status = Status.BUILDING;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getInstructor() {
        return instructor;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }
}
