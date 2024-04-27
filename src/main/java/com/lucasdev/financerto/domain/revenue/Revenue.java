package com.lucasdev.financerto.domain.revenue;

import com.lucasdev.financerto.domain.financetransaction.FinanceTransaction;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "revenues")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Revenue extends FinanceTransaction {
    private CategoryRevenue category;
}
