package com.lirou.store.entities;

import com.lirou.store.DTOs.GlassesDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

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
        setTitle(glassesDTO.title());
        setPrice(glassesDTO.price());
        setPic(glassesDTO.pic());
        setAvailable(glassesDTO.available());
        setColor(glassesDTO.color());
        setModel(glassesDTO.model());
        setBrand(glassesDTO.brand());
    }

    public Glasses() {}

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
