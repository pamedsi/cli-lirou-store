package com.lirou.store.address.application.api;

import com.lirou.store.address.domain.UserAddress;

import java.util.List;

public record UserAddressDTO(
        String identifier,
        String street,
        String number,
        String complement,
        String postalCode,
        String district,
        String city,
        String state,
        String obs
) {
    public UserAddressDTO(UserAddress address){
        this(
                address.getIdentifier(),
                address.getStreet(),
                address.getNumber(),
                address.getComplement(),
                address.getPostalCode(),
                address.getDistrict(),
                address.getCity(),
                address.getState().getAcronym(),
                address.getObs()
        );
    }

    public static List<UserAddressDTO> severalToDTO(List<UserAddress> addresses){
        return addresses.stream().map(UserAddressDTO::new).toList();
    }
}
