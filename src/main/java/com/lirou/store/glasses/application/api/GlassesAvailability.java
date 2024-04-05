package com.lirou.store.glasses.application.api;

import jakarta.validation.constraints.NotNull;

public record GlassesAvailability(
		@NotNull
        Boolean available
) {}
