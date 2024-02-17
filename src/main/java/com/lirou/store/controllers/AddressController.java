package com.lirou.store.controllers;

import com.lirou.store.domain.DTOs.UserAddressDTO;
import com.lirou.store.exceptions.NotFoundException;
import com.lirou.store.services.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.HeaderParam;
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
        log.info("[Finaliza] GlassesService - getAllGlasses()");
        return ResponseEntity.ok(addresses);
    }
    @PostMapping
    public ResponseEntity<?> addNewAddress(@RequestHeader("Authorization") String token, @RequestBody UserAddressDTO addressDTO) throws NotFoundException {
        log.info("[Inicia] AddressService - addNewAddress()");
        addressService.addNewAddress(token, addressDTO);
        log.info("[Finaliza] GlassesService - addNewAddress()");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
