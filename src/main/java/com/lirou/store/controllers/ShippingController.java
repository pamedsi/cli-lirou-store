package com.lirou.store.controllers;

import com.lirou.store.DTOs.PostalCodes;
import com.lirou.store.services.SuperFreteService;
import okhttp3.Response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ShippingController {

    private final SuperFreteService superFreteService;

    public ShippingController(SuperFreteService superFreteService) {
        this.superFreteService = superFreteService;
    }

    @GetMapping("/calculate-shipping")
    public ResponseEntity<?> getGlasses(@RequestBody PostalCodes postalCodes) throws IOException {
        Response response = superFreteService.calculateShipping(postalCodes.from(), postalCodes.to());
        return ResponseEntity.ok(response);
    }
}
