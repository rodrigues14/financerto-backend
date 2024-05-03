package com.lucasdev.financerto.domain.target;

import com.lucasdev.financerto.domain.target.validations.ValidateTargetAndCurrentAmountToRegister;
import com.lucasdev.financerto.domain.target.validations.ValidateTargetAndCurrentAmountToUpdate;
import com.lucasdev.financerto.domain.user.User;
import com.lucasdev.financerto.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class TargetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TargetRepository targetRepository;

    @Autowired
    private ValidateTargetAndCurrentAmountToRegister validateTargetAndCurrentAmountToRegister;

    @Autowired
    private ValidateTargetAndCurrentAmountToUpdate validateTargetAndCurrentAmountToUpdate;

    public ResponseEntity register(Authentication authentication ,TargetDTO data, UriComponentsBuilder uriComponentsBuilder) {
        User currentUser = this.getCurrentUser(authentication);

        validateTargetAndCurrentAmountToRegister.validate(data);
        var target = new Target(currentUser, data);
        targetRepository.save(target);
        var uri = uriComponentsBuilder.path("/target/{id}").buildAndExpand(target.getId()).toUri();
        return ResponseEntity.created(uri).body(new TargetResponseDTO(target));
    }

    public ResponseEntity findById(Authentication authentication, String id) {
        User currentUser = this.getCurrentUser(authentication);
        var target = targetRepository.getReferenceById(id);
        if (!target.getUser().equals(currentUser)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new TargetResponseDTO(target));
    }

    public ResponseEntity<Page<TargetResponseDTO>> list(Authentication authentication, Pageable pageable) {
        User currentUser = this.getCurrentUser(authentication);

        var page = targetRepository.findAllByUserId(currentUser.getId() ,pageable).map(TargetResponseDTO::new);
        return ResponseEntity.ok(page);
    }

    public ResponseEntity update(Authentication authentication, TargetUpdateDTO data) {
        User currentUser = this.getCurrentUser(authentication);
        var target = targetRepository.getReferenceById(data.id());
        if (!target.getUser().equals(currentUser)) {
            return ResponseEntity.notFound().build();
        }
        validateTargetAndCurrentAmountToUpdate.validate(data);
        target.update(data);
        return ResponseEntity.ok(new TargetResponseDTO(target));
    }

    public ResponseEntity delete(Authentication authentication, String id) {
        User currentUser = this.getCurrentUser(authentication);
        var target = targetRepository.findById(id).orElse(null);
        if (target == null || !target.getUser().equals(currentUser)) {
            return ResponseEntity.badRequest().build();
        }
        targetRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private User getCurrentUser(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }

}
