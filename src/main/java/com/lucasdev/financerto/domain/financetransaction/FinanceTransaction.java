package com.lucasdev.financerto.domain.financetransaction;

import com.lucasdev.financerto.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class FinanceTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    private double amount;
    private String description;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private Methods method;
}
