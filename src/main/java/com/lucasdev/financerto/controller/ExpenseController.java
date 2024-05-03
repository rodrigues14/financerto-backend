package com.lucasdev.financerto.controller;

import com.lucasdev.financerto.domain.expense.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    @Transactional
    public ResponseEntity<ExpenseResponseDTO> registerExpense(@RequestBody @Valid ExpenseDTO data, UriComponentsBuilder uriComponentsBuilder, Authentication authentication) {
        return this.expenseService.registerExpense(data, uriComponentsBuilder, authentication);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> findExpenseById(@PathVariable String id, Authentication authentication) {
        return this.expenseService.findExpenseById(id, authentication);
    }

    @GetMapping
    public ResponseEntity<Page<ExpenseResponseDTO>> list(@PageableDefault(size = 10, sort = {"date"}, direction = Sort.Direction.DESC) Pageable pageable, Authentication authentication) {
        return this.expenseService.listExpenses(pageable, authentication);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ExpenseResponseDTO> update(@RequestBody @Valid ExpenseUpdateDTO data, Authentication authentication) {
        return this.expenseService.updateExpense(data, authentication);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable String id, Authentication authentication) {
        return this.expenseService.deleteExpense(id, authentication);
    }

}
