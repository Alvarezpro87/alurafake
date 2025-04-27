package br.com.alura.AluraFake.task;

import jakarta.validation.constraints.*;

public record NewOpenTextTaskDTO(
        @NotNull Long courseId,
        @NotBlank @Size(min = 4, max = 255) String statement,
        @Positive Integer order
) {}
