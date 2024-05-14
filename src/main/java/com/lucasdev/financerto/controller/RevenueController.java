package com.lucasdev.financerto.controller;

import com.lucasdev.financerto.domain.revenue.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearer-key")
public class RevenueController {

    @Autowired
    private RevenueService revenueService;

    @PostMapping
    @Transactional
    public ResponseEntity<RevenueResponseDTO> registerRevenue(@RequestBody @Valid RevenueDTO data, UriComponentsBuilder uriComponentsBuilder, Authentication authentication) {
        RevenueResponseDTO dto = this.revenueService.registerRevenue(data, authentication);
        var uri = uriComponentsBuilder.path("/revenue/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RevenueResponseDTO> findRevenueById(@PathVariable String id, Authentication authentication) {
        RevenueResponseDTO dto = this.revenueService.findRevenueById(id, authentication);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<RevenueResponseDTO>> listRevenues(@PageableDefault(size = 10, sort = {"date"}, direction = Sort.Direction.DESC) Pageable pageable, Authentication authentication) {
        Page<RevenueResponseDTO> page = this.revenueService.listRevenues(pageable, authentication);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<RevenueResponseDTO> updateRevenue(@RequestBody @Valid RevenueUpdateDTO data, Authentication authentication) {
        RevenueResponseDTO revenueUpdated = this.revenueService.updateRevenue(data, authentication);
        return ResponseEntity.ok(revenueUpdated);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteRevenue(@PathVariable String id, Authentication authentication) {
        this.revenueService.deleteRevenue(id, authentication);
        return ResponseEntity.noContent().build();
    }

}
