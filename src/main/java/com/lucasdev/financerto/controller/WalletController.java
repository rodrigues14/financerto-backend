package com.lucasdev.financerto.controller;

import com.lucasdev.financerto.domain.wallet.WalletDTO;
import com.lucasdev.financerto.domain.wallet.WalletService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallet")
@SecurityRequirement(name = "bearer-key")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping
    public ResponseEntity<WalletDTO> wallet(Authentication authentication) {
        var totalRevenue = walletService.calculateTotalRevenues(authentication);
        var totalExpense = walletService.calculateTotalExpenses(authentication);
        var balance = walletService.calculateBalance(authentication);
        return ResponseEntity.ok(new WalletDTO(balance, totalRevenue, totalExpense));
    }

}
