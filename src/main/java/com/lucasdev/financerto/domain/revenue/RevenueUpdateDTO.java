package com.lucasdev.financerto.domain.revenue;

import com.lucasdev.financerto.domain.financetransaction.Methods;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record RevenueUpdateDTO(
        String id,
        @Positive
        Double amount,
        String description,
        @PastOrPresent
        LocalDate date,
        Methods method,
        CategoryRevenue category
) {
        public RevenueUpdateDTO(Revenue revenue) {
                this(revenue.getId(), revenue.getAmount(), revenue.getDescription(),
                        revenue.getDate(), revenue.getMethod(), revenue.getCategory());

        }
}
