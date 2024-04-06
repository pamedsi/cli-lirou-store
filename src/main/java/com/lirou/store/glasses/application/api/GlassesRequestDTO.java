package com.lirou.store.glasses.application.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

public record GlassesRequestDTO(
        @NotBlank
        String title,
        String pic,
        Boolean available,
        int quantityInStock,
        @NotBlank
        String model,
        String frame,
        @NotBlank
        String color,
        @NotBlank
        String brand,
        @NotNull
        @NumberFormat
        BigDecimal price
) {}
