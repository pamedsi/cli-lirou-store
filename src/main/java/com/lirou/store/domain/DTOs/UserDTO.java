package com.lirou.store.domain.DTOs;

import com.lirou.store.Enums.UserRole;

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
