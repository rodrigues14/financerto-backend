package com.lucasdev.financerto.domain.expense;

import com.lucasdev.financerto.domain.financetransaction.Methods;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ExpenseUpdateDTO(
        String id,
        @Positive
        Double amount,
        String description,
        @PastOrPresent
        LocalDate date,
        Methods method,
        String local,
        CategoryExpense category
) {
}
