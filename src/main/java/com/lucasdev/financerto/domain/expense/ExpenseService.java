package com.lucasdev.financerto.domain.expense;

import com.lucasdev.financerto.domain.user.User;
import com.lucasdev.financerto.domain.user.UserRepository;
import com.lucasdev.financerto.utils.RecoverAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ExpenseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private RecoverAuthenticatedUser recoverAuthenticatedUser;

    public ResponseEntity<ExpenseResponseDTO> registerExpense(ExpenseDTO data, UriComponentsBuilder uriComponentsBuilder, Authentication authentication) {
        User currentUser = recoverAuthenticatedUser.getCurrentUser(authentication);
        var expense = new Expense(currentUser, data);
        expenseRepository.save(expense);

        var uri = uriComponentsBuilder.path("/expense/{id}").buildAndExpand(expense.getId()).toUri();
        return ResponseEntity.created(uri).body(new ExpenseResponseDTO(expense));
    }

    public ResponseEntity<ExpenseResponseDTO> findExpenseById(String id, Authentication authentication) {
        User currentUser = recoverAuthenticatedUser.getCurrentUser(authentication);
        var expense = expenseRepository.getReferenceById(id);
        if (!expense.getUser().equals(currentUser)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ExpenseResponseDTO(expense));
    }

    public ResponseEntity<Page<ExpenseResponseDTO>> listExpenses(Pageable pageable, Authentication authentication) {
        User currentUser = recoverAuthenticatedUser.getCurrentUser(authentication);
        var page = expenseRepository.findAllByUserId(currentUser.getId(), pageable).map(ExpenseResponseDTO::new);
        return ResponseEntity.ok(page);
    }

    public ResponseEntity<ExpenseResponseDTO> updateExpense(ExpenseUpdateDTO data, Authentication authentication) {
        User currentUser = recoverAuthenticatedUser.getCurrentUser(authentication);
        var expense = expenseRepository.getReferenceById(data.id());
        if (!expense.getUser().equals(currentUser)) {
            return ResponseEntity.notFound().build();
        }
        expense.update(data);
        return ResponseEntity.ok(new ExpenseResponseDTO(expense));
    }

    public ResponseEntity deleteExpense(String id, Authentication authentication) {
        User currentUser = recoverAuthenticatedUser.getCurrentUser(authentication);
        var expense = this.expenseRepository.findById(id).orElse(null);
        if (expense == null || !expense.getUser().equals(currentUser)) {
            return ResponseEntity.badRequest().build();
        }
        expenseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
