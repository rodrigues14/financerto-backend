package com.lucasdev.financerto.domain.revenue;

import com.lucasdev.financerto.domain.financetransaction.Methods;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record RevenueResponseDTO (
        String id,
        String userId,
        Double amount,
        String description,
        LocalDate date,
        Methods method,
        CategoryRevenue category
) {
    public RevenueResponseDTO(Revenue revenue) {
        this(revenue.getId(), revenue.getUser().getId(), revenue.getAmount(), revenue.getDescription(),
                revenue.getDate(), revenue.getMethod(), revenue.getCategory());
    }
}
