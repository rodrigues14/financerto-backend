package com.lucasdev.financerto.domain.target;

import com.lucasdev.financerto.infra.exceptions.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TargetService {

    @Autowired
    private TargetRepository targetRepository;

    public void validateTargetAndCurrentAmountToRegister(TargetDTO data) {
        if (data.targetAmount() <= data.currentAmount()) {
            throw new ValidateException("The current amount cannot be equal to or greater than the target current");
        }
    }

    public void validateTargetAndCurrentAmountToUpdate(TargetUpdateDTO data) {
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
