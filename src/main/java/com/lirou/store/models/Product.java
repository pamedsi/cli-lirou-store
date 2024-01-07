package com.lirou.store.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column
    private String identifier = UUID.randomUUID().toString();
    @Column (nullable = false, unique = true)
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
    private LocalDateTime createdAt = LocalDateTime.now();

    public String getIdentifier() {
        return identifier;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getPic() {
        return pic;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
