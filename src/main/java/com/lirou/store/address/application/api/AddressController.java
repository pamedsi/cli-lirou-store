package com.lirou.store.address.application.api;

import com.lirou.store.exceptions.NotFoundException;
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
public class AddressController {
    private final AddressService addressService;

    // Por enquanto esse token vai ser só um e-mail
    @GetMapping
    public ResponseEntity<List<UserAddressDTO>> getAddresses(@RequestHeader("Authorization") String token) throws NotFoundException {
        log.info("[Inicia] AddressService - getAllAddresses()");
        List<UserAddressDTO> addresses = addressService.getAllAddresses(token);
        log.info("[Finaliza] AddressService - getAllGlasses()");
        return ResponseEntity.ok(addresses);
    }
    @PostMapping
    public ResponseEntity<?> addNewAddress(@RequestHeader("Authorization") String token, @RequestBody UserAddressDTO addressDTO) throws NotFoundException {
        log.info("[Inicia] AddressService - addNewAddress()");
        addressService.addNewAddress(token, addressDTO);
        log.info("[Finaliza] AddressService - addNewAddress()");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("/{identifier}")
    public ResponseEntity<?> editAddress(@RequestHeader("Authorization") String token, @RequestBody UserAddressDTO addressDTO, @PathVariable("identifier") String addressIdentifier) throws NotFoundException {
        log.info("[Inicia] AddressService - editAddress()");
        addressService.editAddress(token, addressDTO, addressIdentifier);
        log.info("[Finaliza] AddressService - editAddress()");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/{identifier}")
    public ResponseEntity<?> deleteAddress(@RequestHeader("Authorization") String token, @PathVariable("identifier") String addressIdentifier) throws NotFoundException {
        log.info("[Inicia] AddressService - deleteAddress()");
        addressService.deleteAddress(token, addressIdentifier);
        log.info("[Finaliza] AddressService - deleteAddress()");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
