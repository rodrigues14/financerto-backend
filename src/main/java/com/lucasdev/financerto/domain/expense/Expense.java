package com.lucasdev.financerto.domain.expense;

import com.lucasdev.financerto.domain.financetransaction.FinanceTransaction;
import com.lucasdev.financerto.domain.user.User;
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

    public Expense(User user, ExpenseDTO data) {
        this.setUser(user);
        this.setAmount(data.amount());
        this.setDescription(data.description());
        this.setDate(data.date());
        this.setMethod(data.method());
        this.local = data.local();
        this.category = data.category();
    }

    public void update(ExpenseUpdateDTO data) {
        if (data.amount() != null) this.setAmount(data.amount());
        if (data.description() != null) this.setDescription(data.description());
        if (data.date() != null) this.setDate(data.date());
        if (data.method() != null) this.setMethod(data.method());
        if (data.local() != null) this.local = data.local();
        if (data.category() != null) this.category = data.category();
    }
}
