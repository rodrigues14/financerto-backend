package com.lucasdev.financerto.domain.target;

import com.lucasdev.financerto.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TargetDTO(
        @NotBlank
        String userId,
        @NotBlank
        String name,
        @NotNull
        Double targetAmount,
        @NotNull
        Double currentAmount,
        @NotNull
        LocalDate deadline,
        @NotNull
        CategoryTarget category
) {
}
