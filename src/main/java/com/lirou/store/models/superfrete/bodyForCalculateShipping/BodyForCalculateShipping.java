package com.lirou.store.models.superfrete.bodyForCalculateShipping;

public record BodyForCalculateShipping(
        PostalCode from,
        PostalCode to,
        String services,
        PackageDimensions package_dimensions
) {}