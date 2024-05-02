package com.lucasdev.financerto.domain.target;

import jakarta.validation.constraints.NotBlank;

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
