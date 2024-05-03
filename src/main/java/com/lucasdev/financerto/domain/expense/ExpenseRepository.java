package com.lucasdev.financerto.domain.expense;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, String> {
    Page<Expense> findAllByUserId(String userId, Pageable pageable);
}
