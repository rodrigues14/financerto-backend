package com.lucasdev.financerto.domain.target;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record TargetUpdateDTO(
        @NotBlank
        String id,
        String name,
        Double targetAmount,
        Double currentAmount,
        LocalDate deadline,
        CategoryTarget category
) {
}