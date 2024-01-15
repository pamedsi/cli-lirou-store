package com.lirou.store.DTOs.superfrete.bodyForCalculateShipping;

public record BodyForCalculateShipping(
        PostalCode from,
        PostalCode to,
        String services,
        PackageDimensions package_dimensions
) {}