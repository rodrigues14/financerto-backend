package com.lucasdev.financerto.controller;

import com.lucasdev.financerto.domain.expense.Expense;
import com.lucasdev.financerto.domain.expense.ExpenseDTO;
import com.lucasdev.financerto.domain.expense.ExpenseRepository;
import com.lucasdev.financerto.domain.expense.ExpenseUpdateDTO;
import com.lucasdev.financerto.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody ExpenseDTO data, UriComponentsBuilder uriComponentsBuilder) {
        var user = userRepository.getReferenceById(data.userId());
        var expense = new Expense(user, data);
        expenseRepository.save(expense);

        var uri = uriComponentsBuilder.path("/expense/{id}").buildAndExpand(expense.getId()).toUri();
        return ResponseEntity.created(uri).body(new ExpenseDTO(expense));
    }

    @GetMapping("/{id}")
    public ResponseEntity findbyId(@PathVariable String id) {
        var expense = expenseRepository.getReferenceById(id);
        return ResponseEntity.ok(new ExpenseDTO(expense));
    }

    @GetMapping
    public ResponseEntity<Page<ExpenseDTO>> list(@PageableDefault(size = 10, sort = {"date"}, direction = Sort.Direction.DESC) Pageable pageable) {
        var page = expenseRepository.findAll(pageable).map(ExpenseDTO::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody ExpenseUpdateDTO data) {
        var expense = expenseRepository.getReferenceById(data.id());
        expense.update(data);
        return ResponseEntity.ok(new ExpenseDTO(expense));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable String id) {
        expenseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
