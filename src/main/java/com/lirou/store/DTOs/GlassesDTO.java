package com.lirou.store.DTOs;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record GlassesDTO(
        String identifier,
        @NotBlank
        String title,
        String pic,
        @NotBlank
        Boolean inStock,
        @NotBlank
        String model,
        String frame,
        @NotBlank
        String color,
        @NotBlank
        String brand,
        @NotBlank
        BigDecimal price
) {}