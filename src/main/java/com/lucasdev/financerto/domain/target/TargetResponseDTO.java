package com.lucasdev.financerto.domain.target;

import java.time.LocalDate;

public record TargetResponseDTO(
        String id,
        String userId,
        String name,
        Double targetAmount,
        Double currentAmount,
        LocalDate deadline,
        CategoryTarget category,
        String progress
) {
    public TargetResponseDTO(Target target) {
        this(target.getId(), target.getUser().getId(), target.getName(), target.getTargetAmount(),
                target.getCurrentAmount(), target.getDeadline(), target.getCategory(), target.getProgress());
    }
}
