package com.lirou.store.address.application.api;

import com.lirou.store.address.application.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
@Log4j2
@RequiredArgsConstructor
public class AddressController implements AddressAPI{
    private final AddressService addressService;

    @Override
    public ResponseEntity<List<UserAddressDTO>> getAddresses(String token) {
        log.info("[starts] AddressService - getAllAddresses()");
        List<UserAddressDTO> addresses = addressService.getAllAddresses(token);
        log.info("[ends] AddressService - getAllGlasses()");
        return ResponseEntity.ok(addresses);
    }
    @Override
    public ResponseEntity<UserAddressDTO> getAddress(String token,String addressIdentifier) {
        log.info("[starts] AddressService - getAllAddresses()");
        UserAddressDTO addresses = addressService.getAddressWithIdentifier(token, addressIdentifier);
        log.info("[ends] AddressService - getAllGlasses()");
        return ResponseEntity.ok(addresses);
    }
    @Override
    public ResponseEntity<?> addNewAddress(String token, UserAddressDTO addressDTO) {
        log.info("[starts] AddressController - addNewAddress()");
        addressService.addNewAddress(token, addressDTO);
        log.info("[ends] AddressController - addNewAddress()");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Override
    public ResponseEntity<?> editAddress(String token, @RequestBody UserAddressDTO addressDTO, String addressIdentifier) {
        log.info("[starts] AddressController - editAddress()");
        addressService.editAddress(token, addressDTO, addressIdentifier);
        log.info("[ends] AddressController - editAddress()");
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    @Override
    public ResponseEntity<?> deleteAddress(String token, String addressIdentifier) {
        log.info("[starts] AddressController - deleteAddress()");
        addressService.deleteAddress(token, addressIdentifier);
        log.info("[ends] AddressController - deleteAddress()");
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
