package com.lucasdev.financerto.domain.expense;

import com.lucasdev.financerto.domain.financetransaction.Methods;

import java.time.LocalDate;

public record ExpenseResponseDTO (
        String id,
        String userId,
        Double amount,
        String description,
        LocalDate date,
        Methods method,
        String local,
        CategoryExpense category
) {
    public ExpenseResponseDTO(Expense expense) {
        this(expense.getId(),expense.getUser().getId(), expense.getAmount(),
                expense.getDescription(), expense.getDate(), expense.getMethod(),
                expense.getLocal(), expense.getCategory());
    }
}
