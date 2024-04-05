package com.lirou.store.superfrete.application.api.models.bodyForCalculateShipping;

public record PackageDimensions (
        Number height,
        Number width,
        Number length,
        Number weight
) {}