package com.lucasdev.financerto.domain.target;

import com.lucasdev.financerto.domain.target.validations.ValidateTargetAndCurrentAmountToRegister;
import com.lucasdev.financerto.domain.target.validations.ValidateTargetAndCurrentAmountToUpdate;
import com.lucasdev.financerto.domain.user.User;
import com.lucasdev.financerto.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        User currentUser = (User) authentication.getPrincipal();

        validateTargetAndCurrentAmountToRegister.validate(data);
        var target = new Target(currentUser, data);
        targetRepository.save(target);
        var uri = uriComponentsBuilder.path("/target/{id}").buildAndExpand(target.getId()).toUri();
        return ResponseEntity.created(uri).body(new TargetResponseDTO(target));
    }

    public ResponseEntity list(Authentication authentication, Pageable pageable) {
        User currentUser = (User) authentication.getPrincipal();

        var page = targetRepository.findAllByUserId(currentUser.getId() ,pageable).map(TargetResponseDTO::new);
        return ResponseEntity.ok(page);
    }

    public ResponseEntity update(TargetUpdateDTO data) {
        validateTargetAndCurrentAmountToUpdate.validate(data);
        var target = targetRepository.getReferenceById(data.id());
        target.update(data);

        return ResponseEntity.ok(new TargetResponseDTO(target));
    }

}
