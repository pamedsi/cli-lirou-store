package com.lirou.store.DTOs;

import java.math.BigDecimal;

public record InputGlassesDTO(
        String title,
        String pic,
        Boolean inStock,
        String model,
        String color,
        String brand,
        BigDecimal price

) {}
