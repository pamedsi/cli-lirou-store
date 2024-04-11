package com.lirou.store.address.application.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/address")
public interface AddressAPI {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<UserAddressDTO>> getAddresses(@RequestHeader("Authorization") String token);
    @GetMapping("/{addressIdentifier}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<UserAddressDTO> getAddress(@RequestHeader("Authorization") String token, @PathVariable("addressIdentifier") String addressIdentifier);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<?> addNewAddress(@RequestHeader("Authorization") String token, @RequestBody UserAddressDTO addressDTO);

    @PutMapping("/{addressIdentifier}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseEntity<?> editAddress(@RequestHeader("Authorization") String token, @RequestBody UserAddressDTO addressDTO, @PathVariable("addressIdentifier") String addressIdentifier);

    @DeleteMapping("/{addressIdentifier}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseEntity<?> deleteAddress(@RequestHeader("Authorization") String token, @PathVariable("addressIdentifier") String addressIdentifier);
}
