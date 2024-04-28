package com.lucasdev.financerto.domain.expense;

import com.lucasdev.financerto.domain.financetransaction.Methods;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record ExpenseUpdateDTO(
        String id,
        @Positive
        Double amount,
        String description,
        @Past
        LocalDate date,
        Methods method,
        String local,
        CategoryExpense category
) {
}
