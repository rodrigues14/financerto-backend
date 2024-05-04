package com.lucasdev.financerto.domain.revenue;

import com.lucasdev.financerto.domain.financetransaction.Methods;

import java.time.LocalDate;

public record RevenueResponseDTO (
        String id,
        Double amount,
        String description,
        LocalDate date,
        Methods method,
        CategoryRevenue category
) {
    public RevenueResponseDTO(Revenue revenue) {
        this(revenue.getId(), revenue.getAmount(), revenue.getDescription(),
                revenue.getDate(), revenue.getMethod(), revenue.getCategory());
    }
}
