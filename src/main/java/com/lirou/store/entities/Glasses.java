package com.lirou.store.entities;

import com.lirou.store.DTOs.GlassesDTO;
import com.lirou.store.models.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Columns;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
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
        setInStock(glassesDTO.inStock());
        setColor(glassesDTO.color());
        setModel(glassesDTO.model());
        setBrand(glassesDTO.brand());
    }
}
