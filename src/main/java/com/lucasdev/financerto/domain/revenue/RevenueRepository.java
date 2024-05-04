package com.lucasdev.financerto.domain.revenue;

import com.lucasdev.financerto.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RevenueRepository extends JpaRepository<Revenue, String> {
    Page<Revenue> findAllByUserId(String userId, Pageable pageable);

    @Query("SELECT r.amount FROM Revenue r WHERE r.user = :user")
    List<Double> returnAllAmountsByUser(User user);

}
