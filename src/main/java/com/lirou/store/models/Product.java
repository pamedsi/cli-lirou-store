package com.lirou.store.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Boolean inStock;
    @Column
    private Boolean deleted = false;
    @Column
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column
    private LocalDateTime lastEditedIn;
}
