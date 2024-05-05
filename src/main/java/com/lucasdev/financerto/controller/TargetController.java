package com.lucasdev.financerto.controller;

import com.lucasdev.financerto.domain.target.*;
import com.lucasdev.financerto.infra.exceptions.ValidateException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/target")
public class TargetController {

    @Autowired
    private TargetService targetService;

    @PostMapping
    @Transactional
    public ResponseEntity<TargetResponseDTO> registerTarget(@RequestBody @Valid TargetDTO data, UriComponentsBuilder uriComponentsBuilder, Authentication authentication) {
         TargetResponseDTO targetResponseDTO =  targetService.registerTarget(authentication, data);
         var uri = uriComponentsBuilder.path("/target/{id}").buildAndExpand(targetResponseDTO.id()).toUri();
         return ResponseEntity.created(uri).body(targetResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TargetResponseDTO> findTargetById(@PathVariable String id, Authentication authentication) {
        TargetResponseDTO targetResponseDTO = targetService.findTargetById(authentication, id);
        return ResponseEntity.ok(targetResponseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<TargetResponseDTO>> listTargets(@PageableDefault(size = 10, sort = {"deadline"}) Pageable pageable, Authentication authentication) {
        Page<TargetResponseDTO> page =  targetService.listTargets(authentication, pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateTarget(@RequestBody @Valid TargetUpdateDTO data, Authentication authentication) {
        try {
            TargetResponseDTO targetUpdated = targetService.updateTarget(authentication, data);
            return ResponseEntity.ok(targetUpdated);
        } catch (ValidateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteTarget(@PathVariable String id, Authentication authentication) {
        targetService.deleteTarget(authentication, id);
        return ResponseEntity.noContent().build();
    }

}
