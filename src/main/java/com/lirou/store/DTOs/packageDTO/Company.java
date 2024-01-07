package com.lirou.store.DTOs.packageDTO;

public record Company(
   Number id,
   String name,
   String picture,
   Boolean has_error
) {}
