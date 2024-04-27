package com.lucasdev.financerto.domain.revenue;

import com.lucasdev.financerto.domain.financetransaction.FinanceTransaction;
import com.lucasdev.financerto.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "revenues")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Revenue extends FinanceTransaction {
    private CategoryRevenue category;

    public Revenue(User user, RevenueDTO data) {
        this.setUser(user);
        this.setAmount(data.amount());
        this.setDescription(data.description());
        this.setDate(data.date());
        this.setMethod(data.method());
        this.category = data.category();
    }
}
