package com.lucasdev.financerto.domain.revenue;

import com.lucasdev.financerto.domain.financetransaction.Methods;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record RevenueDTO(
        String id,
        @NotBlank
        String userId,
        @NotNull
        Double amount,
        String description,
        @NotNull
        @Past
        LocalDate date,
        @NotNull
        Methods method,
        @NotNull
        CategoryRevenue category
) {
    public RevenueDTO(Revenue revenue) {
        this(revenue.getId(), revenue.getUser().getId(), revenue.getAmount(), revenue.getDescription(),
                revenue.getDate(), revenue.getMethod(), revenue.getCategory());
    }
}
