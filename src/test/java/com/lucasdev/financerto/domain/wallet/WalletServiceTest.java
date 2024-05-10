package com.lucasdev.financerto.domain.wallet;

import com.lucasdev.financerto.domain.expense.ExpenseRepository;
import com.lucasdev.financerto.domain.revenue.RevenueRepository;
import com.lucasdev.financerto.domain.user.User;
import com.lucasdev.financerto.utils.RecoverAuthenticatedUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.List;


@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @InjectMocks
    private WalletService walletService;

    @Mock
    private RevenueRepository revenueRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private User user;

    @Mock
    private Authentication authentication;

    @Mock
    private RecoverAuthenticatedUser recoverAuthenticatedUser;


    @Test
    void shouldCalculateTheTotalRevenue() {
        BDDMockito.given(recoverAuthenticatedUser.getCurrentUser(authentication)).willReturn(user);
        List<Double> revenues = List.of(1000.0, 500.0);
        BDDMockito.given(revenueRepository.returnAllAmountsByUser(user)).willReturn(revenues);

        Assertions.assertEquals(1500.0, walletService.calculateTotalRevenues(authentication));
    }

    @Test
    void shouldCalculateTheTotalExpense() {
        BDDMockito.given(recoverAuthenticatedUser.getCurrentUser(authentication)).willReturn(user);
        List<Double> expenses = List.of(800.0, 350.0);
        BDDMockito.given(expenseRepository.returnAllAmountsByUser(user)).willReturn(expenses);

        Assertions.assertEquals(1150.0, walletService.calculateTotalExpenses(authentication));
    }

    @Test
    void shouldCalculateTheBalance() {
        BDDMockito.given(recoverAuthenticatedUser.getCurrentUser(authentication)).willReturn(user);

        List<Double> revenues = List.of(1000.0, 500.0);
        List<Double> expenses = List.of(800.0, 350.0);

        BDDMockito.given(revenueRepository.returnAllAmountsByUser(user)).willReturn(revenues);
        BDDMockito.given(expenseRepository.returnAllAmountsByUser(user)).willReturn(expenses);

        Assertions.assertEquals(1500.0 - 1150.0, walletService.calculateBalance(authentication));
    }

}