package com.lucasdev.financerto.domain.revenue;

import com.lucasdev.financerto.domain.financetransaction.Methods;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record RevenueDTO(
        @NotNull
        @Positive
        Double amount,
        String description,
        @NotNull
        @PastOrPresent
        LocalDate date,
        @NotNull
        Methods method,
        @NotNull
        CategoryRevenue category
) {
    public RevenueDTO(Revenue revenue) {
        this(revenue.getAmount(), revenue.getDescription(),
                revenue.getDate(), revenue.getMethod(), revenue.getCategory());
    }
}
