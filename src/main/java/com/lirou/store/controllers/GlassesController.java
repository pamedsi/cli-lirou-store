package com.lirou.store.controllers;

import com.lirou.store.DTOs.GlassesDTO;
import com.lirou.store.models.BaseController;
import com.lirou.store.models.Message;
import com.lirou.store.services.GlassesService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GlassesController extends BaseController {

    private final GlassesService glassesService;

    public GlassesController(GlassesService glassesService) {
        this.glassesService = glassesService;
    }

    // Para a parte admin:
    @GetMapping("/get-all-glasses")
    public ResponseEntity<?> getGlasses() {
        List<GlassesDTO> glassesDTO = glassesService.getAllGlasses();
        return ResponseEntity.ok(glassesDTO);
    }
    @PostMapping("/post-glasses")
    public ResponseEntity<?> postGlasses(@RequestBody GlassesDTO glassesDTO) {
        glassesService.saveNewGlasses(glassesDTO);
        return ResponseEntity.ok(new Message(glassesDTO.title() + " salvo!"));
    }
}
