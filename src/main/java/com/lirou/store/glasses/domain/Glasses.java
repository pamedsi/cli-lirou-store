package com.lirou.store.glasses.domain;

import com.lirou.store.glasses.application.api.GlassesRequestDTO;

import com.lirou.store.shared.models.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table
@NoArgsConstructor
public class Glasses extends Product {
    @Column
    private String model;
    @Column
    private String frame;
    @Column
    private String color;
    @Column
    private String brand;

    public Glasses(GlassesRequestDTO glassesDTO) {
        super(glassesDTO.title(), glassesDTO.price(), glassesDTO.pic(), glassesDTO.available());
        this.model = glassesDTO.model();
        this.frame = glassesDTO.frame();
        this.color = glassesDTO.color();
        this.brand = glassesDTO.brand();
    }

    public void updateGlassesFromDTO(Glasses glassesToEdit, GlassesRequestDTO changes) {
        glassesToEdit.setTitle(changes.title());
        glassesToEdit.setPrice(changes.price());
        glassesToEdit.setPic(changes.pic());
        glassesToEdit.setAvailable(changes.available());
        this.model = changes.model();
        this.frame = changes.frame();
        this.color = changes.color();
        this.brand = changes.brand();
    }
}
