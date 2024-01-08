package com.lirou.store.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.lirou.store.DTOs.bodyForCalculateShipping.FromAndToDTO;
import com.lirou.store.DTOs.shippingInfToSendToSuperFrete.ShippingInfToSendToSuperFreteDTO;
import com.lirou.store.services.SuperFreteService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/shipping")
public class ShippingController {

    private final SuperFreteService superFreteService;

    public ShippingController(SuperFreteService superFreteService) {
        this.superFreteService = superFreteService;
    }

    @GetMapping("/calculate")
    public ResponseEntity<?> calculateShipping(@RequestBody FromAndToDTO postalCodes) throws IOException {
        ResponseEntity<?> response = superFreteService.calculateShipping(postalCodes.from(), postalCodes.to());
        return ResponseEntity.ok(response.getBody());
    }
    @PostMapping("/send-to-superfrete")
    public ResponseEntity<?> sendShippingToSuperFrete(@RequestBody ShippingInfToSendToSuperFreteDTO body) throws JsonProcessingException {
        ResponseEntity<?> response = superFreteService.sendShippingToSuperFrete(body);
        return ResponseEntity.ok(response.getBody());
    }
}
