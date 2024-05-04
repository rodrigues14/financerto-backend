package com.lucasdev.financerto.domain.revenue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevenueRepository extends JpaRepository<Revenue, String> {
    Page<Revenue> findAllByUserId(String userId, Pageable pageable);
}
