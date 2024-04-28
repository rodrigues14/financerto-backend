package com.lucasdev.financerto.controller;

import com.lucasdev.financerto.domain.revenue.Revenue;
import com.lucasdev.financerto.domain.revenue.RevenueRepository;
import com.lucasdev.financerto.domain.revenue.RevenueDTO;
import com.lucasdev.financerto.domain.revenue.RevenueUpdateDTO;
import com.lucasdev.financerto.domain.user.User;
import com.lucasdev.financerto.domain.user.UserRepository;
import jakarta.validation.Valid;
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
@RequestMapping("/revenue")
public class RevenueController {

    @Autowired
    private RevenueRepository revenueRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody @Valid RevenueDTO data, UriComponentsBuilder uriComponentsBuilder) {
        User user = userRepository.getReferenceById(data.userId());
        Revenue revenue = new Revenue(user, data);
        revenueRepository.save(revenue);

        var uri = uriComponentsBuilder.path("/revenue/{id}").buildAndExpand(revenue.getId()).toUri();
        return ResponseEntity.created(uri).body(new RevenueDTO(revenue));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        var revenue = revenueRepository.findById(id);
        if (revenue.isPresent()) return ResponseEntity.ok(new RevenueDTO(revenue.get()));
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Page<RevenueDTO>> list(@PageableDefault(size = 10, sort = {"date"}, direction = Sort.Direction.DESC) Pageable pageable) {
        var page = revenueRepository.findAll(pageable).map(RevenueDTO::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid RevenueUpdateDTO data) {
        var revenue = revenueRepository.getReferenceById(data.id());
        revenue.update(data);
        return ResponseEntity.ok(new RevenueUpdateDTO(revenue));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable String id) {
        revenueRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
