package com.lucasdev.financerto.domain.revenue;

import com.lucasdev.financerto.domain.financetransaction.Methods;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record RevenueUpdateDTO(
        String id,
        Double amount,
        String description,
        @Past
        LocalDate date,
        Methods method,
        CategoryRevenue category
) {
        public RevenueUpdateDTO(Revenue revenue) {
                this(revenue.getId(), revenue.getAmount(), revenue.getDescription(),
                        revenue.getDate(), revenue.getMethod(), revenue.getCategory());

        }
}
