package com.lucasdev.financerto.domain.target.validations;

import com.lucasdev.financerto.domain.target.Target;
import com.lucasdev.financerto.domain.target.TargetRepository;
import com.lucasdev.financerto.domain.target.TargetUpdateDTO;
import com.lucasdev.financerto.exceptions.ValidateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidateTargetAndCurrentAmountToUpdateTest {

    @Mock
    private TargetRepository targetRepository;

    @InjectMocks
    ValidateTargetAndCurrentAmountToUpdate validateUpdate = new ValidateTargetAndCurrentAmountToUpdate();

    @Test
    @DisplayName("Should return an exception when the current amount greater than the target and")
    void bothDifferentFromNullCase01() {
        TargetUpdateDTO dto = new TargetUpdateDTO(
                null,"Test", 1000.0, 1200.0, null, null
        );

        Assertions.assertThrows(ValidateException.class, () -> validateUpdate.validate(dto));
    }

    @Test
    @DisplayName("Must confirm, when the current amount is less than or equal to the target and")
    void bothDifferentFromNullCase02() {
        TargetUpdateDTO dto = new TargetUpdateDTO(
                null,"Test", 1000.0, 900.0, null, null
        );

        Assertions.assertDoesNotThrow(() -> validateUpdate.validate(dto));
    }

    @Test
    @DisplayName("Should return an exception when the current Amount (has not been updated) is greater than the target(has been updated)")
    void currentAmountIsNullAndTheTargetIsNotCase1() {
        Target target = new Target(
                "id5", null, null, 1000.0, 800.0, null, null, null
        );
        TargetUpdateDTO dto = new TargetUpdateDTO("ab5", null, 500.0, null, null, null);

        BDDMockito.given(targetRepository.getReferenceById(dto.id())).willReturn(target);
        Assertions.assertThrows(ValidateException.class, () -> validateUpdate.validate(dto));
    }

    @Test
    @DisplayName("Must confirm, when the current Amount (has not been updated) is less than or equal to the target(has been updated)")
    void currentAmountIsNullAndTheTargetIsNotCase2() {
        Target target = new Target(
                "id5", null, null, 1000.0, 800.0, null, null, null
        );
        TargetUpdateDTO dto = new TargetUpdateDTO("ab5", null, 800.0, null, null, null);

        BDDMockito.given(targetRepository.getReferenceById(dto.id())).willReturn(target);
        Assertions.assertDoesNotThrow(() -> validateUpdate.validate(dto));
    }

    @Test
    @DisplayName("Should return an exception when current amount (has been updated) is greater then the target (has not been updated)")
    void targetAmountIsNullAndTheCurrentIsNotCase1() {
        Target target = new Target(
                "id5", null, null, 1000.0, 700.0, null, null, null
        );
        TargetUpdateDTO dto = new TargetUpdateDTO("ab5", null, null, 1100.0, null, null);

        BDDMockito.given(targetRepository.getReferenceById(dto.id())).willReturn(target);
        Assertions.assertThrows(ValidateException.class, () -> validateUpdate.validate(dto));
    }

    @Test
    @DisplayName("Must confirm, when current amount (has been updated) is less then or equal to the target (has not been updated)")
    void targetAmountIsNullAndTheCurrentIsNotCase2() {
        Target target = new Target(
                "id5", null, null, 1000.0, 700.0, null, null, null
        );
        TargetUpdateDTO dto = new TargetUpdateDTO("ab5", null, null, 900.0, null, null);

        BDDMockito.given(targetRepository.getReferenceById(dto.id())).willReturn(target);
        Assertions.assertDoesNotThrow(() -> validateUpdate.validate(dto));
    }

}