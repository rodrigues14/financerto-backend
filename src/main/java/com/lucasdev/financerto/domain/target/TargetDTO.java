package com.lucasdev.financerto.domain.target;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record TargetDTO(
        @NotBlank
        String name,
        @NotNull
        @Positive
        Double targetAmount,
        @NotNull
        @PositiveOrZero
        Double currentAmount,
        @NotNull
        @Future
        LocalDate deadline,
        @NotNull
        CategoryTarget category
) {
}
