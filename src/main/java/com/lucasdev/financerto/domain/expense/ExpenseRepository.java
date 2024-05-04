package com.lucasdev.financerto.domain.expense;

import com.lucasdev.financerto.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, String> {
    Page<Expense> findAllByUserId(String userId, Pageable pageable);

    @Query("SELECT e.amount FROM Expense e WHERE e.user = :user")
    List<Double> returnAllAmountsByUser(User user);
}
