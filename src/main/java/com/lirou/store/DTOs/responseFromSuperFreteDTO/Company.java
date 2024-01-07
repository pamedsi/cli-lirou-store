package com.lirou.store.DTOs.responseFromSuperFreteDTO;

public record Company(
   Number id,
   String name,
   String picture,
   Boolean has_error
) {}
