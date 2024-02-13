package com.lirou.store.domain.entities;

import com.lirou.store.models.State;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Table
@RequiredArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String identifier = UUID.randomUUID().toString();
    @ManyToOne
    private UserEntity owner;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String number;

    @Column
    private String complemento;

    @Column
    private String bairro;

    @Column
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 2)
    private State state;

    @Column(nullable = false, length = 8)
    private String postalCode;

    @Column
    private String obs;
}
