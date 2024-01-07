package com.lirou.store.DTOs.bodyForCalculateShipping;

public record PackageDimensions (
        Number height,
        Number width,
        Number length,
        Number weight
) {}