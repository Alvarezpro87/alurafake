package br.com.alura.AluraFake.task;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public record NewSingleChoiceTaskDTO(
        @NotNull Long courseId,
        @NotBlank @Size(min = 4, max = 255) String statement,
        @Positive Integer order,
        @Size(min = 2, max = 5) List<@Valid OptionDTO> options
) {}