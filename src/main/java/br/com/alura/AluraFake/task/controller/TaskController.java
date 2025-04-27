package br.com.alura.AluraFake.task.controller;

import br.com.alura.AluraFake.task.dto.NewMultipleChoiceTaskDTO;
import br.com.alura.AluraFake.task.dto.NewOpenTextTaskDTO;
import br.com.alura.AluraFake.task.dto.NewSingleChoiceTaskDTO;
import br.com.alura.AluraFake.task.service.TaskService;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    private final TaskService service;
    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping("/new/opentext")
    public ResponseEntity<Void> newOpenTextExercise(@RequestBody @Validated NewOpenTextTaskDTO dto) {
        service.createOpenText(dto);
        return ResponseEntity.created(URI.create("/task")).build();
    }

    @PostMapping("/new/singlechoice")
    public ResponseEntity<Void> newSingleChoice(@RequestBody @Validated NewSingleChoiceTaskDTO dto) {
        service.createSingleChoice(dto);
        return ResponseEntity.created(URI.create("/task")).build();
    }

    @PostMapping("/new/multiplechoice")
    public ResponseEntity<Void> newMultipleChoice(@RequestBody @Validated NewMultipleChoiceTaskDTO dto) {
        service.createMultipleChoice(dto);
        return ResponseEntity.created(URI.create("/task")).build();
    }

}