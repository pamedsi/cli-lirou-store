package com.lirou.store.domain.DTOs;

import com.lirou.store.Enums.State;
import com.lirou.store.domain.entities.AddressEntity;

import java.util.List;

public record UserAddressDTO(
        String identifier,
        String street,
        String number,
        String complement,
        String postalCode,
        String neighborhood,
        String city,
        String state,
        String obs
) {
    public UserAddressDTO(AddressEntity address){
        this(
                address.getIdentifier(),
                address.getStreet(),
                address.getNumber(),
                address.getComplement(),
                address.getPostalCode(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState().getAcronym(),
                address.getObs()
        );
    }

    public static List<UserAddressDTO> severalToDTO(List<AddressEntity> addresses){
        return addresses.stream().map(UserAddressDTO::new).toList();
    }
}
