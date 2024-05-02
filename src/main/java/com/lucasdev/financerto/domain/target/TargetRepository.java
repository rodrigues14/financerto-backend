package com.lucasdev.financerto.domain.target;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TargetRepository extends JpaRepository<Target, String> {
    Page<Target> findAllByUserId(String id, Pageable pageable);
}
