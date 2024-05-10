package com.lucasdev.financerto.domain.target.validations;

import com.lucasdev.financerto.domain.target.TargetDTO;
import com.lucasdev.financerto.exceptions.ValidateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidateTargetAndCurrentAmountToRegisterTest {

    @InjectMocks
    private ValidateTargetAndCurrentAmountToRegister validate;

    @Mock
    private TargetDTO targetDTO;

    @Test
    void currentValueShouldBeGreaterThanTheCurrentAmount() {
        BDDMockito.given(targetDTO.targetAmount()).willReturn(1000.0);
        BDDMockito.given(targetDTO.currentAmount()).willReturn(700.0);

        Assertions.assertDoesNotThrow(() -> validate.validate(targetDTO));
    }

    @Test
    void shouldReturnExceptionWhenCurrentAmountIsGreaterThanTarget() {
        BDDMockito.given(targetDTO.targetAmount()).willReturn(1000.0);
        BDDMockito.given(targetDTO.currentAmount()).willReturn(1200.0);

        Assertions.assertThrows(ValidateException.class, () -> validate.validate(targetDTO));
    }

    @Test
    void shouldReturnExceptionWhenCurrentAmountIsEqualToTarget() {
        BDDMockito.given(targetDTO.targetAmount()).willReturn(1000.0);
        BDDMockito.given(targetDTO.currentAmount()).willReturn(1000.0);

        Assertions.assertThrows(ValidateException.class, () -> validate.validate(targetDTO));
    }

}