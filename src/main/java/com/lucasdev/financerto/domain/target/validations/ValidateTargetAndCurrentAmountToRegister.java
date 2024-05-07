package com.lucasdev.financerto.domain.target.validations;

import com.lucasdev.financerto.domain.target.TargetDTO;
import com.lucasdev.financerto.exceptions.ValidateException;
import org.springframework.stereotype.Component;

@Component
public class ValidateTargetAndCurrentAmountToRegister {

    public void validate(TargetDTO data) {
        if (data.targetAmount() <= data.currentAmount()) {
            throw new ValidateException("The current amount cannot be equal to or greater than the target current");
        }
    }

}
