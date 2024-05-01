package com.lucasdev.financerto.domain.target;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record TargetDTO(
        @NotBlank
        String userId,
        @NotBlank
        String name,
        @NotNull
        @Positive
        Double targetAmount,
        @NotNull
        @Positive
        Double currentAmount,
        @NotNull
        @Future
        LocalDate deadline,
        @NotNull
        CategoryTarget category
) {
}
