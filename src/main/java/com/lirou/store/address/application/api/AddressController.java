package com.lirou.store.address.application.api;

import com.lirou.store.handler.exceptions.NotFoundException;
import com.lirou.store.address.application.service.AddressApplicationService;
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
    private final AddressApplicationService addressApplicationService;

    @GetMapping
    public ResponseEntity<List<UserAddressDTO>> getAddresses(@RequestHeader("Authorization") String token) {
        log.info("[starts] AddressService - getAllAddresses()");
        List<UserAddressDTO> addresses = addressApplicationService.getAllAddresses(token);
        log.info("[ends] AddressService - getAllGlasses()");
        return ResponseEntity.ok(addresses);
    }
    @GetMapping("/{addressIdentifier}")
    public ResponseEntity<UserAddressDTO> getAddress(@RequestHeader("Authorization") String token, @PathVariable("addressIdentifier") String addressIdentifier) {
        log.info("[starts] AddressService - getAllAddresses()");
        UserAddressDTO addresses = addressApplicationService.getAddressWithIdentifier(token, addressIdentifier);
        log.info("[ends] AddressService - getAllGlasses()");
        return ResponseEntity.ok(addresses);
    }
    @PostMapping
    public ResponseEntity<?> addNewAddress(@RequestHeader("Authorization") String token, @RequestBody UserAddressDTO addressDTO) throws NotFoundException {
        log.info("[starts] AddressController - addNewAddress()");
        addressApplicationService.addNewAddress(token, addressDTO);
        log.info("[ends] AddressController - addNewAddress()");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("/{identifier}")
    public ResponseEntity<?> editAddress(@RequestHeader("Authorization") String token, @RequestBody UserAddressDTO addressDTO, @PathVariable("identifier") String addressIdentifier) throws NotFoundException {
        log.info("[starts] AddressController - editAddress()");
        addressApplicationService.editAddress(token, addressDTO, addressIdentifier);
        log.info("[ends] AddressController - editAddress()");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/{identifier}")
    public ResponseEntity<?> deleteAddress(@RequestHeader("Authorization") String token, @PathVariable("identifier") String addressIdentifier) throws NotFoundException {
        log.info("[starts] AddressController - deleteAddress()");
        addressApplicationService.deleteAddress(token, addressIdentifier);
        log.info("[ends] AddressController - deleteAddress()");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
