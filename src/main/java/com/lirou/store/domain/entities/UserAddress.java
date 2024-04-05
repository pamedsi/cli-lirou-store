package com.lirou.store.domain.entities;

import com.lirou.store.enums.State;
import com.lirou.store.domain.DTOs.UserAddressDTO;
import com.lirou.store.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table (name = "address")
@RequiredArgsConstructor
@Data
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column
    @Setter(AccessLevel.NONE)
    private String identifier;
    @ManyToOne
    @Setter(AccessLevel.NONE)
    private User owner;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String number;
    @Column
    private String complement;
    @Column
    private String district;
    @Column
    private String city;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State state;
    @Column(nullable = false)
    private String postalCode;
    @Column
    private String obs;
    @Column
    private Boolean deleted;
    @Column
    private LocalDateTime createdAt;

    public UserAddress(UserAddressDTO addressDTO, User user){
        this.identifier = UUID.randomUUID().toString();
        this.owner = user;
        this.street = addressDTO.street();
        this.number = addressDTO.number();
        this.complement = addressDTO.complement();
        this.district = addressDTO.district();
        this.city = addressDTO.city();
        this.state = State.fromAcronym(addressDTO.state());
        this.postalCode = addressDTO.postalCode();
        this.obs = addressDTO.obs();
        this.deleted = false;
        this.createdAt = LocalDateTime.now();
    }

    public void setState(String state) {
        this.state = State.fromAcronym(state);
    }
}
