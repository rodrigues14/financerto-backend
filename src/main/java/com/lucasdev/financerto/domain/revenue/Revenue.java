package com.lucasdev.financerto.domain.revenue;

import com.lucasdev.financerto.domain.financetransaction.FinanceTransaction;
import com.lucasdev.financerto.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Enumerated(EnumType.STRING)
    private CategoryRevenue category;

    public Revenue(User user, RevenueDTO data) {
        this.setUser(user);
        this.setAmount(data.amount());
        this.setDescription(data.description());
        this.setDate(data.date());
        this.setMethod(data.method());
        this.category = data.category();
    }

    public void update(RevenueUpdateDTO data) {
        if (data.amount() != null) this.setAmount(data.amount());
        if (data.description() != null) this.setDescription(data.description());
        if (data.date() != null) this.setDate(data.date());
        if (data.method() != null) this.setMethod(data.method());
        if (data.category() != null) this.category = data.category();
    }
}
