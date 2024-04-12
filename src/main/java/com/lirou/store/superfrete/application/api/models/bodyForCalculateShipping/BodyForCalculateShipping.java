package com.lirou.store.superfrete.application.api.models.bodyForCalculateShipping;

public record BodyForCalculateShipping(
        PostalCode from,
        PostalCode to,
        String services,
        PackageDimensions package_dimensions
) {}