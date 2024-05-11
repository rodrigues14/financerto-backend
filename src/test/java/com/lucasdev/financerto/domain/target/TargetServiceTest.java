package com.lucasdev.financerto.domain.target;

import com.lucasdev.financerto.domain.target.validations.ValidateTargetAndCurrentAmountToRegister;
import com.lucasdev.financerto.domain.target.validations.ValidateTargetAndCurrentAmountToUpdate;
import com.lucasdev.financerto.domain.user.User;
import com.lucasdev.financerto.utils.RecoverAuthenticatedUser;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TargetServiceTest {

    @InjectMocks
    private TargetService targetService;

    @Mock
    private RecoverAuthenticatedUser recoverAuthenticatedUser;

    @Mock
    private TargetRepository targetRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private User user;

    @Mock
    private ValidateTargetAndCurrentAmountToRegister validateAmountsRegister;

    @Captor
    private ArgumentCaptor<Target> targetCaptor;

    @Mock
    private Target target;

    @Mock
    private User user2;

    @Mock
    private Page<Target> listTarget;

    @Mock
    private TargetUpdateDTO targetUpdateDTO;

    @Mock
    private ValidateTargetAndCurrentAmountToUpdate validateTargetAndCurrentAmountToUpdate;

    @Test
    void shouldRegisterTarget() {
        LocalDate deadline = LocalDate.now().plusDays(5);
        TargetDTO targetDto = new TargetDTO("My target", 5000.0, 1000.0, deadline, CategoryTarget.CARREIRA);

        targetService.registerTarget(authentication, targetDto);

        BDDMockito.then(targetRepository).should().save(targetCaptor.capture());
        var targetExpected = new Target(user, targetDto);
        Assertions.assertEquals(targetExpected, targetCaptor.getValue());
    }

    @Test
    void shouldFindTargetById() {
        String id = "1";
        BDDMockito.given(target.getUser()).willReturn(user);
        BDDMockito.given(recoverAuthenticatedUser.getCurrentUser(authentication)).willReturn(user);
        BDDMockito.given(targetRepository.getReferenceById(id)).willReturn(target);

        targetService.findTargetById(authentication, id);

        BDDMockito.then(targetRepository).should().getReferenceById(id);
    }

    @Test
    void shouldNotFindTargetThatBelongsToAnotherUser() {
        String idTarget = "1";
        BDDMockito.given(target.getUser()).willReturn(user2);
        BDDMockito.given(recoverAuthenticatedUser.getCurrentUser(authentication)).willReturn(user);
        BDDMockito.given(targetRepository.getReferenceById(idTarget)).willReturn(target);

        Assertions.assertThrows(EntityNotFoundException.class, () -> targetService.findTargetById(authentication, idTarget));
    }

    @Test
    void shouldListTargetPerUser() {
        BDDMockito.given(recoverAuthenticatedUser.getCurrentUser(authentication)).willReturn(user);
        BDDMockito.given(targetRepository.findAllByUserId(user.getId(), Pageable.unpaged())).willReturn(listTarget);

        targetService.listTargets(authentication, Pageable.unpaged());

        BDDMockito.then(targetRepository).should().findAllByUserId(user.getId(), Pageable.unpaged());
    }

    @Test
    void shouldUpdateTarget() {
        String idTarget = "1";
        BDDMockito.given(recoverAuthenticatedUser.getCurrentUser(authentication)).willReturn(user);
        BDDMockito.given(targetRepository.getReferenceById(idTarget)).willReturn(target);
        BDDMockito.given(target.getUser()).willReturn(user);
        BDDMockito.given(targetUpdateDTO.id()).willReturn(idTarget);

        targetService.updateTarget(authentication, targetUpdateDTO);

        BDDMockito.then(target).should().update(targetUpdateDTO);
    }

    @Test
    void shouldNotUpdateTargetBelongsToAnotherUser() {
        String idTarget = "1";
        BDDMockito.given(recoverAuthenticatedUser.getCurrentUser(authentication)).willReturn(user);
        BDDMockito.given(targetRepository.getReferenceById(idTarget)).willReturn(target);
        BDDMockito.given(target.getUser()).willReturn(user2);
        BDDMockito.given(targetUpdateDTO.id()).willReturn(idTarget);

        Assertions.assertThrows(EntityNotFoundException.class, () -> targetService.updateTarget(authentication, targetUpdateDTO));
    }

    @Test
    void shouldDeleteTarget() {
        String idTarget = "1";
        BDDMockito.given(recoverAuthenticatedUser.getCurrentUser(authentication)).willReturn(user);
        BDDMockito.given(targetRepository.findById(idTarget)).willReturn(Optional.of(target));
        BDDMockito.given(target.getUser()).willReturn(user);

        targetService.deleteTarget(authentication, idTarget);

        BDDMockito.then(targetRepository).should().deleteById(idTarget);
    }

    @Test
    void shouldNotDeleteWhenTargetIsNull() {
        String idTarget = "1";
        BDDMockito.given(recoverAuthenticatedUser.getCurrentUser(authentication)).willReturn(user);
        BDDMockito.given(targetRepository.findById(idTarget)).willReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> targetService.deleteTarget(authentication, idTarget));
    }

    @Test
    void shouldNotDeleteTargetBelongsToAnotherUser() {
        String idTarget = "1";
        BDDMockito.given(recoverAuthenticatedUser.getCurrentUser(authentication)).willReturn(user);
        BDDMockito.given(targetRepository.findById(idTarget)).willReturn(Optional.of(target));
        BDDMockito.given(target.getUser()).willReturn(user2);

        Assertions.assertThrows(EntityNotFoundException.class, () -> targetService.deleteTarget(authentication, idTarget));
    }

}