package com.lucasdev.financerto.domain.expense;

import com.lucasdev.financerto.domain.financetransaction.Methods;
import com.lucasdev.financerto.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record ExpenseDTO(
        @NotBlank
        String userId,
        @NotNull
        @Positive
        Double amount,
        String description,
        @NotNull
        @Past
        LocalDate date,
        @NotNull
        Methods method,
        String local,
        @NotNull
        CategoryExpense category
) {
        public ExpenseDTO(Expense expense) {
                this(expense.getUser().getId(), expense.getAmount(),
                        expense.getDescription(), expense.getDate(), expense.getMethod(),
                        expense.getLocal(), expense.getCategory());
        }
}
