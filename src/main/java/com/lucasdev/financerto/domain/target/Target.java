package com.lucasdev.financerto.domain.target;

import com.lucasdev.financerto.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "targets")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Target {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    private String name;
    private double targetAmount;
    private double currentAmount;
    private LocalDate deadline;
    @Enumerated(EnumType.STRING)
    private CategoryTarget category;
    private String progress;

    public Target(User user, TargetDTO data) {
        this.user = user;
        this.name = data.name();
        this.targetAmount = data.targetAmount();
        this.currentAmount = data.currentAmount();
        this.deadline = data.deadline();
        this.category = data.category();
        this.progress = calculateProgress(data.targetAmount(), data.currentAmount());
    }

    public void update(TargetUpdateDTO data) {
        if (data.name() != null) this.name = data.name();
        if (data.targetAmount() != null) this.targetAmount = data.targetAmount();
        if (data.currentAmount() != null) this.currentAmount = data.currentAmount();
        if (data.deadline() != null) this.deadline = data.deadline();
        if (data.category() != null) this.category = data.category();

        if (data.targetAmount() != null && data.currentAmount() != null) {
            this.progress = this.calculateProgress(data.targetAmount(), data.currentAmount());
        } else if (data.targetAmount() != null) {
            this.progress = this.calculateProgress(data.targetAmount(), this.currentAmount);
        } else if (data.currentAmount() != null) {
            this.progress = this.calculateProgress(this.targetAmount, data.currentAmount());
        }
    }

    private String calculateProgress(Double targetAmount, Double currentAmount) {
        var progress = ((currentAmount / targetAmount) * 100);
        return String.format("%.1f", progress) + "%";
    }

}
