package com.lucasdev.financerto.domain.expense;

import com.lucasdev.financerto.domain.financetransaction.FinanceTransaction;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "expenses")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Expense extends FinanceTransaction {
    private String local;
    private CategoryExpense category;
}
