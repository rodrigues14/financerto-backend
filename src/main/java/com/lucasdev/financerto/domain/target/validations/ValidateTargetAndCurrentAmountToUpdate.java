package com.lucasdev.financerto.domain.target.validations;

import com.lucasdev.financerto.domain.target.TargetRepository;
import com.lucasdev.financerto.domain.target.TargetUpdateDTO;
import com.lucasdev.financerto.infra.exceptions.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateTargetAndCurrentAmountToUpdate {

    @Autowired
    private TargetRepository targetRepository;

    public void validate(TargetUpdateDTO data) {
        var targetBeforeUpdate = targetRepository.getReferenceById(data.id());
        var targetAmount = data.targetAmount();
        var currentAmount = data.currentAmount();

        if (targetAmount != null && currentAmount != null && currentAmount > targetAmount) {
            throw new ValidateException("The current amount cannot be greater than the target current");
        }
        else if (targetAmount != null && currentAmount == null) {
            currentAmount = targetBeforeUpdate.getCurrentAmount();
            if (currentAmount > targetAmount) {
                throw new ValidateException("The current amount cannot be greater than the target current");
            }
        }
        else if (targetAmount == null && currentAmount != null) {
            targetAmount = targetBeforeUpdate.getTargetAmount();
            if (currentAmount > targetAmount) {
                throw new ValidateException("The current amount cannot be greater than the target current");
            }
        }
    }

}
