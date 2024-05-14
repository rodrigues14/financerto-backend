package com.lucasdev.financerto.domain.expense;

import com.lucasdev.financerto.domain.user.User;
import com.lucasdev.financerto.utils.RecoverAuthenticatedUser;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private RecoverAuthenticatedUser recoverAuthenticatedUser;

    public ExpenseResponseDTO registerExpense(ExpenseDTO data, Authentication authentication) {
        User currentUser = recoverAuthenticatedUser.getCurrentUser(authentication);
        var expense = new Expense(currentUser, data);
        expenseRepository.save(expense);
        return new ExpenseResponseDTO(expense);
    }

    public ExpenseResponseDTO findExpenseById(String id, Authentication authentication) {
        User currentUser = recoverAuthenticatedUser.getCurrentUser(authentication);
        var expense = expenseRepository.getReferenceById(id);
        if (!expense.getUser().equals(currentUser)) {
            throw new EntityNotFoundException();
        }
        return new ExpenseResponseDTO(expense);
    }

    public Page<ExpenseResponseDTO> listExpenses(Pageable pageable, Authentication authentication) {
        User currentUser = recoverAuthenticatedUser.getCurrentUser(authentication);
        return expenseRepository.findAllByUserId(currentUser.getId(), pageable).map(ExpenseResponseDTO::new);
    }

    public ExpenseResponseDTO updateExpense(ExpenseUpdateDTO data, Authentication authentication) {
        User currentUser = recoverAuthenticatedUser.getCurrentUser(authentication);
        var expense = expenseRepository.getReferenceById(data.id());
        if (!expense.getUser().equals(currentUser)) {
            throw new EntityNotFoundException();
        }
        expense.update(data);
        return new ExpenseResponseDTO(expense);
    }

    public void deleteExpense(String id, Authentication authentication) {
        User currentUser = recoverAuthenticatedUser.getCurrentUser(authentication);
        var expense = this.expenseRepository.findById(id).orElse(null);
        if (expense == null || !expense.getUser().equals(currentUser)) {
            throw new EntityNotFoundException();
        }
        expenseRepository.deleteById(id);
    }
}
