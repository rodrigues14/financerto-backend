package com.lucasdev.financerto.domain.revenue;

import com.lucasdev.financerto.domain.user.User;
import com.lucasdev.financerto.utils.RecoverAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RevenueService {

    @Autowired
    private RecoverAuthenticatedUser recoverAuthenticatedUser;

    @Autowired
    private RevenueRepository revenueRepository;

    public ResponseEntity<RevenueResponseDTO> registerRevenue(RevenueDTO data, UriComponentsBuilder uriComponentsBuilder, Authentication authentication) {
        User currentUser = this.recoverAuthenticatedUser.getCurrentUser(authentication);
        Revenue revenue = new Revenue(currentUser, data);
        revenueRepository.save(revenue);
        var uri = uriComponentsBuilder.path("/revenue/{id}").buildAndExpand(revenue.getId()).toUri();
        return ResponseEntity.created(uri).body(new RevenueResponseDTO(revenue));
    }

    public ResponseEntity<RevenueResponseDTO> findRevenueById(String id, Authentication authentication) {
        User currentUser = this.recoverAuthenticatedUser.getCurrentUser(authentication);
        var revenue = revenueRepository.getReferenceById(id);
        if (!revenue.getUser().equals(currentUser)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new RevenueResponseDTO(revenue));
    }

    public ResponseEntity<Page<RevenueResponseDTO>> listRevenues(Pageable pageable, Authentication authentication) {
        User currentUser = this.recoverAuthenticatedUser.getCurrentUser(authentication);
        var page = revenueRepository.findAllByUserId(currentUser.getId(), pageable).map(RevenueResponseDTO::new);
        return ResponseEntity.ok(page);
    }

    public ResponseEntity<RevenueResponseDTO> updateRevenue(RevenueUpdateDTO data, Authentication authentication) {
        User currentUser = this.recoverAuthenticatedUser.getCurrentUser(authentication);
        var revenue = revenueRepository.getReferenceById(data.id());
        if (!revenue.getUser().equals(currentUser)) {
            return ResponseEntity.notFound().build();
        }
        revenue.update(data);
        return ResponseEntity.ok(new RevenueResponseDTO(revenue));
    }

    public ResponseEntity deleteRevenue(String id, Authentication authentication) {
        User currentUser = this.recoverAuthenticatedUser.getCurrentUser(authentication);
        Revenue revenue = revenueRepository.findById(id).orElse(null);
        if (revenue == null || !revenue.getUser().equals(currentUser)) {
            return ResponseEntity.badRequest().build();
        }
        revenueRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
