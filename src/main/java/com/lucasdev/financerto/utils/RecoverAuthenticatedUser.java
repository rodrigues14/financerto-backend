package com.lucasdev.financerto.utils;


import com.lucasdev.financerto.domain.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class RecoverAuthenticatedUser {
    public User getCurrentUser(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }
}
