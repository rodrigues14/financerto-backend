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
}
