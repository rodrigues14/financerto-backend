package com.lucasdev.financerto.domain.target;

import com.lucasdev.financerto.domain.target.validations.ValidateTargetAndCurrentAmountToRegister;
import com.lucasdev.financerto.domain.target.validations.ValidateTargetAndCurrentAmountToUpdate;
import com.lucasdev.financerto.domain.user.User;
import com.lucasdev.financerto.utils.RecoverAuthenticatedUser;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TargetService {

    @Autowired
    private TargetRepository targetRepository;

    @Autowired
    private ValidateTargetAndCurrentAmountToRegister validateTargetAndCurrentAmountToRegister;

    @Autowired
    private ValidateTargetAndCurrentAmountToUpdate validateTargetAndCurrentAmountToUpdate;

    @Autowired
    private RecoverAuthenticatedUser recoverAuthenticatedUser;

    public TargetResponseDTO registerTarget(Authentication authentication ,TargetDTO data) {
        User currentUser = recoverAuthenticatedUser.getCurrentUser(authentication);
        validateTargetAndCurrentAmountToRegister.validate(data);
        var target = new Target(currentUser, data);
        targetRepository.save(target);
        return new TargetResponseDTO(target);
    }

    public TargetResponseDTO findTargetById(Authentication authentication, String id) {
        User currentUser = recoverAuthenticatedUser.getCurrentUser(authentication);
        var target = targetRepository.getReferenceById(id);
        if (!target.getUser().equals(currentUser)) {
            throw new EntityNotFoundException();
        }
        return new TargetResponseDTO(target);
    }

    public Page<TargetResponseDTO> listTargets(Authentication authentication, Pageable pageable) {
        User currentUser = recoverAuthenticatedUser.getCurrentUser(authentication);
        return targetRepository.findAllByUserId(currentUser.getId() ,pageable).map(TargetResponseDTO::new);
    }

    public TargetResponseDTO updateTarget(Authentication authentication, TargetUpdateDTO data) {
        User currentUser = recoverAuthenticatedUser.getCurrentUser(authentication);
        var target = targetRepository.getReferenceById(data.id());
        if (!target.getUser().equals(currentUser)) {
            throw new EntityNotFoundException();
        }
        validateTargetAndCurrentAmountToUpdate.validate(data);
        target.update(data);
        return new TargetResponseDTO(target);
    }

    public void deleteTarget(Authentication authentication, String id) {
        User currentUser = recoverAuthenticatedUser.getCurrentUser(authentication);
        var target = targetRepository.findById(id).orElse(null);
        if (target == null || !target.getUser().equals(currentUser)) {
            throw new EntityNotFoundException();
        }
        targetRepository.deleteById(id);
    }

}
