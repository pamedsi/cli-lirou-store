package com.lirou.store.user.application.api;

import com.lirou.store.user.domain.UserRole;

import java.time.LocalDate;
import java.util.Optional;

public record UserDTO(
        String name,
        String email,
        String password,
        LocalDate birthDate,
        Optional<String> CPF,
        UserRole role
) {}
