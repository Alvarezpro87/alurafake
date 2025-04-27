package br.com.alura.AluraFake.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OptionDTO(
        @NotBlank
        @Size(min = 4, max = 80)
        String option,
        boolean isCorrect
) {}