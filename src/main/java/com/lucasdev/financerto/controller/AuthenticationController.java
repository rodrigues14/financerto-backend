package com.lucasdev.financerto.controller;

import com.lucasdev.financerto.domain.user.*;
import com.lucasdev.financerto.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var authentication = authenticationManager.authenticate(authenticationToken);

        var token = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new tokenDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (userRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), data.email(), encryptedPassword);

        userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }

}
