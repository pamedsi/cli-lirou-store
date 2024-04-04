package com.lirou.store.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Data
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Long id;
    @Column
    @Setter(AccessLevel.NONE)
    private String identifier = UUID.randomUUID().toString();
    @Column (nullable = false)
    private String title;
    @Column
    private BigDecimal price;
    @Column (length = 2000)
    private String pic;
    @Column
    private Boolean available;
    @Column
    private int quantityInStock;
    @Column
    private Boolean deleted = false;
    @Column
    @Setter (AccessLevel.NONE)
    private LocalDateTime createdAt = LocalDateTime.now();
    public void decrementQuantity() { this.quantityInStock--; }

    public Product(String title, BigDecimal price, String pic, Boolean available) {
        this.title = title;
        this.price = price;
        this.pic = pic;
        this.available = available;
    }
}
