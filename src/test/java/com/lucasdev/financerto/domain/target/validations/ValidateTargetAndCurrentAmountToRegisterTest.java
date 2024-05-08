package com.lucasdev.financerto.domain.target.validations;

import com.lucasdev.financerto.domain.target.TargetDTO;
import com.lucasdev.financerto.exceptions.ValidateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateTargetAndCurrentAmountToRegisterTest {

    ValidateTargetAndCurrentAmountToRegister validate = new ValidateTargetAndCurrentAmountToRegister();

    @Test
    @DisplayName("Should return an exception because the current amount is greater than or equal to the target")
    void currentAmountGreaterThanOrEqualToTarget() {
        TargetDTO dto = new TargetDTO(
                "Test", 1000.0, 1000.0, null, null
        );

        Assertions.assertThrows(ValidateException.class, () -> validate.validate(dto));
    }

    @Test
    @DisplayName("It should be allowed, because the current amount is lower than the target")
    void currentAmountLessThanTarget() {
        TargetDTO dto = new TargetDTO(
                "Test", 1000.0, 800.0, null, null
        );

        Assertions.assertDoesNotThrow(() -> validate.validate(dto));
    }

}