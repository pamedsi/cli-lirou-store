package com.lirou.store.entities;

import com.lirou.store.DTOs.GlassesDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table
@RequiredArgsConstructor
public class Glasses extends Product {
    @Column
    private String model;
    @Column
    private String frame;
    @Column
    private String color;
    @Column
    private String brand;

    public Glasses(GlassesDTO glassesDTO) {
        setTitle(glassesDTO.title());
        setPrice(glassesDTO.price());
        setPic(glassesDTO.pic());
        setAvailable(glassesDTO.available());
        setColor(glassesDTO.color());
        setModel(glassesDTO.model());
        setBrand(glassesDTO.brand());
        setFrame(glassesDTO.frame());
    }
}
