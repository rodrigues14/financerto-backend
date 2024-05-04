package com.lucasdev.financerto.controller;

import com.lucasdev.financerto.domain.revenue.*;
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
@RequestMapping("/revenue")
public class RevenueController {

    @Autowired
    private RevenueRepository revenueRepository;

    @Autowired
    private RevenueService revenueService;

    @PostMapping
    @Transactional
    public ResponseEntity<RevenueResponseDTO> registerRevenue(@RequestBody @Valid RevenueDTO data, UriComponentsBuilder uriComponentsBuilder, Authentication authentication) {
        return this.revenueService.registerRevenue(data, uriComponentsBuilder, authentication);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RevenueResponseDTO> findRevenueById(@PathVariable String id, Authentication authentication) {
        return this.revenueService.findRevenueById(id, authentication);
    }

    @GetMapping
    public ResponseEntity<Page<RevenueResponseDTO>> listRevenues(@PageableDefault(size = 10, sort = {"date"}, direction = Sort.Direction.DESC) Pageable pageable, Authentication authentication) {
        return this.revenueService.listRevenues(pageable, authentication);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<RevenueResponseDTO> updateRevenue(@RequestBody @Valid RevenueUpdateDTO data, Authentication authentication) {
        return this.revenueService.updateRevenue(data, authentication);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteRevenue(@PathVariable String id, Authentication authentication) {
        return this.revenueService.deleteRevenue(id, authentication);
    }

}
