package com.lirou.store.controllers;

import com.lirou.store.DTOs.GlassesDTO;
import com.lirou.store.models.Message;
import com.lirou.store.services.GlassesService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GlassesController{

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
        return ResponseEntity.status(201).body(new Message(glassesDTO.title() + "salvo!"));
    }

    @PutMapping("/put-glasses")
    public ResponseEntity<?> putGlasses(@RequestBody @Valid GlassesDTO glassesDTO) {
        glassesService.updateGlasses(glassesDTO);
        return ResponseEntity.ok(new Message("Atualização feita com sucesso!"));
    }

    @DeleteMapping("/delete-glasses/{identifier}")
    public ResponseEntity<?> deleteGlasses(@PathVariable("identifier") String glassesIdentifier) {
        String titleOfDeletedGlasses = glassesService.removeGlasses(glassesIdentifier);
        return ResponseEntity.ok(new Message(titleOfDeletedGlasses + "deletado!"));
    }
}
