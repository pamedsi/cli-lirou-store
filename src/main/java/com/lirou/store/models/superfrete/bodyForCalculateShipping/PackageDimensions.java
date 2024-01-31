package com.lirou.store.models.superfrete.bodyForCalculateShipping;

public record PackageDimensions (
        Number height,
        Number width,
        Number length,
        Number weight
) {}