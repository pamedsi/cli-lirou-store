package com.lirou.store.domain.entities;

import com.lirou.store.domain.DTOs.GlassesDTO;
import com.lirou.store.domain.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table
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
        super(glassesDTO.title(), glassesDTO.price(), glassesDTO.pic(), glassesDTO.available());
        this.model = glassesDTO.model();
        this.frame = glassesDTO.frame();
        this.color = glassesDTO.color();
        this.brand = glassesDTO.brand();
    }

    public Glasses() {
        super(null, null, null, null);
    }
}
