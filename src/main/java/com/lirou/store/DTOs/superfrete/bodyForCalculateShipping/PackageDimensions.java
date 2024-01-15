package com.lirou.store.DTOs.superfrete.bodyForCalculateShipping;

public record PackageDimensions (
        Number height,
        Number width,
        Number length,
        Number weight
) {}