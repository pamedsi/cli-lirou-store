package com.lirou.store.DTOs;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record GlassesDTO(
        String identifier,
        @NotBlank
        String title,
        String pic,
        Boolean inStock,
        String model,
        String color,
        String brand,
        BigDecimal price
) {}