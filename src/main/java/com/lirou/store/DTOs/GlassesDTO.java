package com.lirou.store.DTOs;

import com.lirou.store.entities.Glasses;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;

public record GlassesDTO(
        String identifier,
        @NotBlank
        String title,
        String pic,
        Boolean inStock,
        @NotBlank
        String model,
        @NotBlank
        String frame,
        @NotBlank
        String color,
        @NotBlank
        String brand,
        @NotBlank
        BigDecimal price
) {
        public GlassesDTO(Glasses glassesEntity) {
                this(
                        glassesEntity.getIdentifier(),
                        glassesEntity.getTitle(),
                        glassesEntity.getPic(),
                        glassesEntity.getInStock(),
                        glassesEntity.getModel(),
                        glassesEntity.getColor(),
                        glassesEntity.getFrame(),
                        glassesEntity.getBrand(),
                        glassesEntity.getPrice()
                );
        }

        public static List<GlassesDTO> severalToDTO (List<Glasses> glassesEntities) {
                return glassesEntities.stream().map(GlassesDTO::new).toList();
        }
}