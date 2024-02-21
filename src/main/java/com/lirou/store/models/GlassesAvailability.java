package com.lirou.store.models;

import jakarta.validation.constraints.NotNull;

public record GlassesAvailability(
		@NotNull
        Boolean available
) {}
