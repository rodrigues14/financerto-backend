package com.lucasdev.financerto.domain.revenue;

import com.lucasdev.financerto.domain.user.User;
import com.lucasdev.financerto.utils.RecoverAuthenticatedUser;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class RevenueService {

    @Autowired
    private RecoverAuthenticatedUser recoverAuthenticatedUser;

    @Autowired
    private RevenueRepository revenueRepository;

    public RevenueResponseDTO registerRevenue(RevenueDTO data, Authentication authentication) {
        User currentUser = this.recoverAuthenticatedUser.getCurrentUser(authentication);
        Revenue revenue = new Revenue(currentUser, data);
        revenueRepository.save(revenue);
        return new RevenueResponseDTO(revenue);
    }

    public RevenueResponseDTO findRevenueById(String id, Authentication authentication) {
        User currentUser = this.recoverAuthenticatedUser.getCurrentUser(authentication);
        var revenue = revenueRepository.getReferenceById(id);
        if (!revenue.getUser().equals(currentUser)) {
            throw new EntityNotFoundException();
        }
        return new RevenueResponseDTO(revenue);
    }

    public Page<RevenueResponseDTO> listRevenues(Pageable pageable, Authentication authentication) {
        User currentUser = this.recoverAuthenticatedUser.getCurrentUser(authentication);
        return revenueRepository.findAllByUserId(currentUser.getId(), pageable).map(RevenueResponseDTO::new);
    }

    public RevenueResponseDTO updateRevenue(RevenueUpdateDTO data, Authentication authentication) {
        User currentUser = this.recoverAuthenticatedUser.getCurrentUser(authentication);
        var revenue = revenueRepository.getReferenceById(data.id());
        if (!revenue.getUser().equals(currentUser)) {
            throw new EntityNotFoundException();
        }
        revenue.update(data);
        return new RevenueResponseDTO(revenue);
    }

    public void deleteRevenue(String id, Authentication authentication) {
        User currentUser = this.recoverAuthenticatedUser.getCurrentUser(authentication);
        Revenue revenue = revenueRepository.findById(id).orElse(null);
        if (revenue == null || !revenue.getUser().equals(currentUser)) {
            throw new EntityNotFoundException();
        }
        revenueRepository.deleteById(id);
    }

}
