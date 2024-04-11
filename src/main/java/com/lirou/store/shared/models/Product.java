package com.lirou.store.shared.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Long id;
    @Column
    @Setter(AccessLevel.NONE)
    private String identifier;
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
    private LocalDateTime createdAt;

    public void decrementQuantity() { this.quantityInStock--; }

    public Product(String title, BigDecimal price, String pic, Boolean available) {
        this.title = title;
        this.price = price;
        this.pic = pic;
        this.available = available;
        this.identifier = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }
}
