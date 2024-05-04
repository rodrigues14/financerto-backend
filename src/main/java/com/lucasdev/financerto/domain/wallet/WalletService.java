package com.lucasdev.financerto.domain.wallet;

import com.lucasdev.financerto.domain.expense.ExpenseRepository;
import com.lucasdev.financerto.domain.revenue.RevenueRepository;
import com.lucasdev.financerto.domain.user.User;
import com.lucasdev.financerto.utils.RecoverAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

@Service
public class WalletService {

    @Autowired
    private RecoverAuthenticatedUser recoverAuthenticatedUser;

    @Autowired
    private RevenueRepository revenueRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ENGLISH);
    DecimalFormat df = new DecimalFormat("#.##", symbols);

    public Double calculateTotalRevenues(Authentication authentication) {
        User currentUser = this.recoverAuthenticatedUser.getCurrentUser(authentication);
        List<Double> revenueAmounts = this.revenueRepository.returnAllAmountsByUser(currentUser);
        return addAmountsAndConvertToBigDecimal(revenueAmounts);
    }

    public Double calculateTotalExpenses(Authentication authentication) {
        User currentUser = this.recoverAuthenticatedUser.getCurrentUser(authentication);
        List<Double> expenseAmounts = this.expenseRepository.returnAllAmountsByUser(currentUser);
        return addAmountsAndConvertToBigDecimal(expenseAmounts);
    }

    public Double calculateBalance(Authentication authentication) {
        Double balance = calculateTotalRevenues(authentication) - calculateTotalExpenses(authentication);
        return Double.valueOf(df.format(balance));
    }

    private Double addAmountsAndConvertToBigDecimal(List<Double> list) {
        Double totalAmount = list
                .stream()
                .reduce(0.0, Double::sum);
        return Double.valueOf(df.format(totalAmount));
    }

}
