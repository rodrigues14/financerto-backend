package com.lucasdev.financerto.controller;

import com.lucasdev.financerto.domain.expense.*;
import com.lucasdev.financerto.domain.user.UserRepository;
import com.lucasdev.financerto.infra.exceptions.ValidateException;
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
        var user = userRepository.findById(data.userId());
        if (user.isPresent()) {
            var expense = new Expense(user.get(), data);
            expenseRepository.save(expense);

            var uri = uriComponentsBuilder.path("/expense/{id}").buildAndExpand(expense.getId()).toUri();
            return ResponseEntity.created(uri).body(new ExpenseResponseDTO(expense));
        }
        return ResponseEntity.badRequest().body("Invalid user id");
    }

    @GetMapping("/{id}")
    public ResponseEntity findbyId(@PathVariable String id) {
        var expense = expenseRepository.getReferenceById(id);
        return ResponseEntity.ok(new ExpenseResponseDTO(expense));
    }

    @GetMapping
    public ResponseEntity<Page<ExpenseResponseDTO>> list(@PageableDefault(size = 10, sort = {"date"}, direction = Sort.Direction.DESC) Pageable pageable) {
        var page = expenseRepository.findAll(pageable).map(ExpenseResponseDTO::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody ExpenseUpdateDTO data) {
        var expense = expenseRepository.getReferenceById(data.id());
        expense.update(data);
        return ResponseEntity.ok(new ExpenseResponseDTO(expense));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable String id) {
        expenseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
