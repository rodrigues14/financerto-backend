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
    private ExpenseService expenseService;

    @PostMapping
    @Transactional
    public ResponseEntity<ExpenseResponseDTO> registerExpense(@RequestBody @Valid ExpenseDTO data, UriComponentsBuilder uriComponentsBuilder, Authentication authentication) {
        ExpenseResponseDTO dto = this.expenseService.registerExpense(data, authentication);
        var uri = uriComponentsBuilder.path("/expense/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> findExpenseById(@PathVariable String id, Authentication authentication) {
        ExpenseResponseDTO dto = this.expenseService.findExpenseById(id, authentication);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<ExpenseResponseDTO>> listExpenses(@PageableDefault(size = 10, sort = {"date"}, direction = Sort.Direction.DESC) Pageable pageable, Authentication authentication) {
        Page<ExpenseResponseDTO> page = this.expenseService.listExpenses(pageable, authentication);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateExpense(@RequestBody @Valid ExpenseUpdateDTO data, Authentication authentication) {
        ExpenseResponseDTO expenseUpdate = this.expenseService.updateExpense(data, authentication);
        return ResponseEntity.ok(expenseUpdate);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteExpense(@PathVariable String id, Authentication authentication) {
        this.expenseService.deleteExpense(id, authentication);
        return ResponseEntity.noContent().build();
    }

}
