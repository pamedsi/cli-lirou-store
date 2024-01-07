package com.lirou.store.controllers;

import com.lirou.store.DTOs.bodyForCalculateShipping.FromAndTo;
import com.lirou.store.services.SuperFreteService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ShippingController {

    private final SuperFreteService superFreteService;

    public ShippingController(SuperFreteService superFreteService) {
        this.superFreteService = superFreteService;
    }

    @GetMapping("/calculate-shipping")
    public ResponseEntity<?> calculateShipping(@RequestBody FromAndTo postalCodes) throws IOException {
        ResponseEntity<?> response = superFreteService.calculateShipping(postalCodes.from(), postalCodes.to());
        return ResponseEntity.ok(response.getBody());
    }
}
