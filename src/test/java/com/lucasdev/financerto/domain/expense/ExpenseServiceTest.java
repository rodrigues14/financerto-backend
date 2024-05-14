package com.lucasdev.financerto.domain.expense;

import com.lucasdev.financerto.domain.financetransaction.Methods;
import com.lucasdev.financerto.domain.user.User;
import com.lucasdev.financerto.utils.RecoverAuthenticatedUser;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @InjectMocks
    private ExpenseService expenseService;

    @Mock
    private Authentication authentication;

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private RecoverAuthenticatedUser recoverAuthenticatedUser;

    @Mock
    private Expense expense;

    @Captor
    private ArgumentCaptor<Expense> argumentCaptor;

    @Mock
    private User user;

    @Mock
    private User otherUser;

    @Mock
    private ExpenseUpdateDTO expenseUpdateDTO;

    @Test
    void shouldRegisterExpense() {
        ExpenseDTO expenseDTO = new ExpenseDTO(100.0, "Description", LocalDate.now().plusDays(-1), Methods.CHEQUE, "local", CategoryExpense.OUTRA);

        expenseService.registerExpense(expenseDTO, authentication);

        BDDMockito.then(expenseRepository).should().save(argumentCaptor.capture());
        var expenseExpected = new Expense(user, expenseDTO);
        Assertions.assertEquals(expenseExpected, argumentCaptor.getValue());
    }

    @Test
    void shouldUpdateExpense() {
        String id = "1";
        BDDMockito.given(recoverAuthenticatedUser.getCurrentUser(authentication)).willReturn(user);
        BDDMockito.given(expenseRepository.getReferenceById(id)).willReturn(expense);
        BDDMockito.given(expense.getUser()).willReturn(user);
        BDDMockito.given(expenseUpdateDTO.id()).willReturn(id);

        expenseService.updateExpense(expenseUpdateDTO, authentication);

        BDDMockito.then(expense).should().update(expenseUpdateDTO);
    }

    @Test
    void shouldDeleteExpense() {
        String id = "1";
        BDDMockito.given(recoverAuthenticatedUser.getCurrentUser(authentication)).willReturn(user);
        BDDMockito.given(expenseRepository.findById(id)).willReturn(Optional.of(expense));
        BDDMockito.given(expense.getUser()).willReturn(user);

        expenseService.deleteExpense(id, authentication);

        BDDMockito.then(expenseRepository).should().deleteById(id);
    }

    @Test
    void shouldNotDeleteExpenseWhenExpenseIsNull() {
        String id = "1";
        BDDMockito.given(recoverAuthenticatedUser.getCurrentUser(authentication)).willReturn(user);
        BDDMockito.given(expenseRepository.findById(id)).willReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> expenseService.deleteExpense(id, authentication));
    }

    @Test
    void shouldNotDeleteExpenseWhenExpenseBelongsToAnotherUser() {
        String id = "1";
        BDDMockito.given(recoverAuthenticatedUser.getCurrentUser(authentication)).willReturn(user);
        BDDMockito.given(expenseRepository.findById(id)).willReturn(Optional.of(expense));
        BDDMockito.given(expense.getUser()).willReturn(otherUser);

        Assertions.assertThrows(EntityNotFoundException.class, () -> expenseService.deleteExpense(id, authentication));
    }

}