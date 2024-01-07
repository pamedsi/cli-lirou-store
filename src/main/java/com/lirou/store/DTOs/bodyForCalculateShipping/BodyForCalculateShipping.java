package com.lirou.store.DTOs.bodyForCalculateShipping;

public record BodyForCalculateShipping(
        PostalCode from,
        PostalCode to,
        String services,
        PackageDimensions package_dimensions
) {}