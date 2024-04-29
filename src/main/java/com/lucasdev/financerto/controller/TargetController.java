package com.lucasdev.financerto.controller;

import com.lucasdev.financerto.domain.target.*;
import com.lucasdev.financerto.domain.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/target")
public class TargetController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TargetRepository targetRepository;

    @Autowired
    private TargetService targetService;

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody @Valid TargetDTO data, UriComponentsBuilder uriComponentsBuilder) {
        targetService.validateTargetAndCurrentAmountToRegister(data);
        var user = userRepository.getReferenceById(data.userId());
        var target = new Target(user, data);
        targetRepository.save(target);

        var uri = uriComponentsBuilder.path("/target/{id}").buildAndExpand(target.getId()).toUri();
        return ResponseEntity.created(uri).body(new TargetResponseDTO(target));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        var target = targetRepository.getReferenceById(id);
        return ResponseEntity.ok(new TargetResponseDTO(target));
    }

    @GetMapping
    public ResponseEntity<Page<TargetResponseDTO>> list(@PageableDefault(size = 10, sort = {"deadline"}) Pageable pageable) {
        var page = targetRepository.findAll(pageable).map(TargetResponseDTO::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody TargetUpdateDTO data) {
        targetService.validateTargetAndCurrentAmountToUpdate(data);
        var target = targetRepository.getReferenceById(data.id());
        target.update(data);

        return ResponseEntity.ok(new TargetResponseDTO(target));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable String id) {
        targetRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
