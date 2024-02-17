package com.lirou.store.domain.entities;

import com.lirou.store.Enums.State;
import com.lirou.store.domain.DTOs.UserAddressDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Table (name = "address")
@RequiredArgsConstructor
@Getter
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter(AccessLevel.NONE)
    private Long id;
    @Column
    private String identifier = UUID.randomUUID().toString();
    @ManyToOne
    private User owner;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String number;

    @Column
    private String complement;

    @Column
    private String neighborhood;

    @Column
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 2)
    private State state;

    @Column(nullable = false, length = 8)
    private String postalCode;

    @Column
    private String obs;

    public AddressEntity(UserAddressDTO addressDTO, User user){
        this.owner = user;
        this.street = addressDTO.street();
        this.number = addressDTO.number();
        this.complement = addressDTO.complement();
        this.neighborhood = addressDTO.neighborhood();
        this.city = addressDTO.city();
        this.state = addressDTO.state();
        this.postalCode = addressDTO.postalCode();
        this.obs = addressDTO.obs();
    }
}
