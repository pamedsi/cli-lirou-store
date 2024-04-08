package com.lirou.store.address.domain;

import com.lirou.store.address.application.api.UserAddressDTO;
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
        identifier = UUID.randomUUID().toString();
        owner = user;
        street = addressDTO.street();
        number = addressDTO.number();
        complement = addressDTO.complement();
        district = addressDTO.district();
        city = addressDTO.city();
        state = State.fromAcronym(addressDTO.state());
        postalCode = addressDTO.postalCode();
        obs = addressDTO.obs();
        deleted = false;
        createdAt = LocalDateTime.now();
    }

    public void updateAddressFromDTO(UserAddressDTO addressDTO) {
        city = addressDTO.city();
        obs = addressDTO.obs();
        complement = addressDTO.complement();
        number = addressDTO.number();
        state = State.valueOf(addressDTO.state());
        district = addressDTO.district();
        street = addressDTO.street();
        postalCode = addressDTO.postalCode();
    }
}
