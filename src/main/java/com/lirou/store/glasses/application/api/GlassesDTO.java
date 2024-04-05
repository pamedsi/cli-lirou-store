package com.lirou.store.glasses.application.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public record GlassesDTO(
        String identifier,
        @NotBlank
        String title,
        String pic,
        Boolean available,
        int quantityInStock,
        @NotBlank
        String model,
        String frame,
        @NotBlank
        String color,
        @NotBlank
        String brand,
        @NotNull
        @NumberFormat
        BigDecimal price
) {
        public GlassesDTO(com.lirou.store.glasses.domain.Glasses glassesEntity) {
                this(
                        glassesEntity.getIdentifier(),
                        glassesEntity.getTitle(),
                        glassesEntity.getPic(),
                        glassesEntity.getAvailable(),
                        glassesEntity.getQuantityInStock(),
                        glassesEntity.getModel(),
                        glassesEntity.getFrame(),
                        glassesEntity.getColor(),
                        glassesEntity.getBrand(),
                        glassesEntity.getPrice()
                );
        }

        public static List<GlassesDTO> severalToDTO(List<com.lirou.store.glasses.domain.Glasses> glassesEntities) {
                return glassesEntities.stream().map(GlassesDTO::new).collect(Collectors.toList());
        }

        public static Page<GlassesDTO> toPageDTO(Page<com.lirou.store.glasses.domain.Glasses> page) {
                List<GlassesDTO> dtoList = severalToDTO(page.getContent());
                return new PageImpl<>(dtoList, page.getPageable(), page.getTotalElements());
        }
}