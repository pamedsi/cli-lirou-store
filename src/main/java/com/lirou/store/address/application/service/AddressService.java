package com.lirou.store.address.application.service;

import com.lirou.store.address.application.api.UserAddressDTO;

import java.util.List;

public interface AddressService {
    List<UserAddressDTO> getAllAddresses(String token);
    UserAddressDTO getAddressWithIdentifier(String token, String identifier);
    void addNewAddress(String token, UserAddressDTO addressDTO);
    void editAddress(String token, UserAddressDTO addressDTO, String identifier);
    void deleteAddress(String token, String addressIdentifier);
}
