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

@ExtendWith(MockitoExtension.class)
class ValidateTargetAndCurrentAmountToUpdateTest {

    @Mock
    private TargetRepository targetRepository;

    @InjectMocks
    private ValidateTargetAndCurrentAmountToUpdate validate;

    @Mock
    private TargetUpdateDTO targetUpdateDTO;

    @Mock
    private Target targetBeforeUpdate;

    @Test
    @DisplayName("Should return an exception when the current amount greater than the target and")
    void bothDifferentFromNullCase01() {
        BDDMockito.given(targetUpdateDTO.targetAmount()).willReturn(1000.0);
        BDDMockito.given(targetUpdateDTO.currentAmount()).willReturn(1100.0);

        Assertions.assertThrows(ValidateException.class, () -> validate.validate(targetUpdateDTO));
    }

    @Test
    @DisplayName("No should return an exception when the current amount is less than the target and")
    void bothDifferentFromNullCase02() {
        BDDMockito.given(targetUpdateDTO.targetAmount()).willReturn(1000.0);
        BDDMockito.given(targetUpdateDTO.currentAmount()).willReturn(900.0);

        Assertions.assertDoesNotThrow(() -> validate.validate(targetUpdateDTO));
    }

    @Test
    @DisplayName("No should return an exception when the current amount is igual to the target and")
    void bothDifferentFromNullCase03() {
        BDDMockito.given(targetUpdateDTO.targetAmount()).willReturn(1000.0);
        BDDMockito.given(targetUpdateDTO.currentAmount()).willReturn(1000.0);

        Assertions.assertDoesNotThrow(() -> validate.validate(targetUpdateDTO));
    }

    @Test
    @DisplayName("Should return an exception when the current Amount (has not been updated) is greater than the target(has been updated)")
    void currentAmountIsNullAndTheTargetIsNotCase1() {
        BDDMockito.given(targetRepository.getReferenceById(targetUpdateDTO.id())).willReturn(targetBeforeUpdate);
        BDDMockito.given(targetUpdateDTO.targetAmount()).willReturn(800.0);
        BDDMockito.given(targetUpdateDTO.currentAmount()).willReturn(null);

        BDDMockito.given(targetBeforeUpdate.getCurrentAmount()).willReturn(1000.0);

        Assertions.assertThrows(ValidateException.class, () -> validate.validate(targetUpdateDTO));
    }

    @Test
    @DisplayName("No should return an exception when the current Amount (has not been updated) is less than the target(has been updated)")
    void currentAmountIsNullAndTheTargetIsNotCase2() {
        BDDMockito.given(targetRepository.getReferenceById(targetUpdateDTO.id())).willReturn(targetBeforeUpdate);
        BDDMockito.given(targetUpdateDTO.targetAmount()).willReturn(1500.0);
        BDDMockito.given(targetUpdateDTO.currentAmount()).willReturn(null);

        BDDMockito.given(targetBeforeUpdate.getCurrentAmount()).willReturn(1000.0);

        Assertions.assertDoesNotThrow(() -> validate.validate(targetUpdateDTO));
    }

    @Test
    @DisplayName("No should return an exception when the current Amount (has not been updated) is equal than the target(has been updated)")
    void currentAmountIsNullAndTheTargetIsNotCase3() {
        BDDMockito.given(targetRepository.getReferenceById(targetUpdateDTO.id())).willReturn(targetBeforeUpdate);
        BDDMockito.given(targetUpdateDTO.targetAmount()).willReturn(1000.0);
        BDDMockito.given(targetUpdateDTO.currentAmount()).willReturn(null);

        BDDMockito.given(targetBeforeUpdate.getCurrentAmount()).willReturn(1000.0);

        Assertions.assertDoesNotThrow(() -> validate.validate(targetUpdateDTO));
    }

    @Test
    @DisplayName("Should return an exception when current amount (has been updated) is greater than the target (has not been updated)")
    void targetAmountIsNullAndTheCurrentIsNotCase1() {
        BDDMockito.given(targetRepository.getReferenceById(targetUpdateDTO.id())).willReturn(targetBeforeUpdate);
        BDDMockito.given(targetUpdateDTO.targetAmount()).willReturn(null);
        BDDMockito.given(targetUpdateDTO.currentAmount()).willReturn(2000.0);

        BDDMockito.given(targetBeforeUpdate.getTargetAmount()).willReturn(1500.0);

        Assertions.assertThrows(ValidateException.class, () -> validate.validate(targetUpdateDTO));
    }

    @Test
    @DisplayName("No should return an exception when current amount (has been updated) is less than the target (has not been updated)")
    void targetAmountIsNullAndTheCurrentIsNotCase2() {
        BDDMockito.given(targetRepository.getReferenceById(targetUpdateDTO.id())).willReturn(targetBeforeUpdate);
        BDDMockito.given(targetUpdateDTO.targetAmount()).willReturn(null);
        BDDMockito.given(targetUpdateDTO.currentAmount()).willReturn(1000.0);

        BDDMockito.given(targetBeforeUpdate.getTargetAmount()).willReturn(1500.0);

        Assertions.assertDoesNotThrow(() -> validate.validate(targetUpdateDTO));
    }

    @Test
    @DisplayName("No should return an exception when current amount (has been updated) is equal to the target (has not been updated)")
    void targetAmountIsNullAndTheCurrentIsNotCase3() {
        BDDMockito.given(targetRepository.getReferenceById(targetUpdateDTO.id())).willReturn(targetBeforeUpdate);
        BDDMockito.given(targetUpdateDTO.targetAmount()).willReturn(null);
        BDDMockito.given(targetUpdateDTO.currentAmount()).willReturn(1500.0);

        BDDMockito.given(targetBeforeUpdate.getTargetAmount()).willReturn(1500.0);

        Assertions.assertDoesNotThrow(() -> validate.validate(targetUpdateDTO));
    }

}