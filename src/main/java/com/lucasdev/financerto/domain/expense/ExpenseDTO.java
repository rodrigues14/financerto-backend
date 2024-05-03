package com.lucasdev.financerto.domain.expense;

import com.lucasdev.financerto.domain.financetransaction.Methods;
import com.lucasdev.financerto.domain.user.User;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ExpenseDTO(
        @NotNull
        @Positive
        Double amount,
        String description,
        @NotNull
        @PastOrPresent
        LocalDate date,
        @NotNull
        Methods method,
        String local,
        @NotNull
        CategoryExpense category
) {
        public ExpenseDTO(Expense expense) {
                this(expense.getAmount(),
                        expense.getDescription(), expense.getDate(), expense.getMethod(),
                        expense.getLocal(), expense.getCategory());
        }
}
