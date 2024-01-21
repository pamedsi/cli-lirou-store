package com.lirou.store.controllers;

import com.lirou.store.DTOs.GlassesDTO;

import com.lirou.store.models.GlassesAvailability;
import com.lirou.store.models.Message;
import com.lirou.store.services.GlassesService;

import com.lirou.store.validation.identifierValidator.ValidIdentifier;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/glasses")
public class GlassesController{

    private final GlassesService glassesService;

    public GlassesController(GlassesService glassesService) {
        this.glassesService = glassesService;
    }

    // Para a parte admin:
    @GetMapping
    public ResponseEntity<Page<GlassesDTO>> getGlasses() {
        Page<GlassesDTO> glassesDTO = glassesService.getAllGlasses();
        return ResponseEntity.ok(glassesDTO);
    }
    @GetMapping("/{identifier}")
    public ResponseEntity<GlassesDTO> findSingleGlasses(@PathVariable("identifier") @ValidIdentifier String identifier) {
        GlassesDTO glassesDTO = glassesService.findGlassesByIdentifier(identifier);
        return ResponseEntity.ok(glassesDTO);
    }
    @PostMapping
    public ResponseEntity<Message> postGlasses(@RequestBody GlassesDTO glassesDTO) {
        glassesService.saveNewGlasses(glassesDTO);
        return ResponseEntity.status(201).body(new Message(glassesDTO.title() + " salvo!"));
    }

    @PutMapping("/{identifier}")
    public ResponseEntity<Message> putGlasses(@RequestBody @Valid GlassesDTO glassesDTO, @PathVariable("identifier") String glassesIdentifier) {
        glassesService.updateGlasses(glassesIdentifier, glassesDTO);
        return ResponseEntity.ok(new Message("Atualização feita com sucesso!"));
    }

    @DeleteMapping("/{identifier}")
    public ResponseEntity<Message> deleteGlasses(@PathVariable("identifier") String glassesIdentifier) {
        String titleOfDeletedGlasses = glassesService.removeGlasses(glassesIdentifier);
        return ResponseEntity.ok(new Message(titleOfDeletedGlasses + "deletado!"));
    }
  
    @PatchMapping("/set-available/{identifier}")
    public ResponseEntity<Message> changeAvailability(@PathVariable("identifier") String glassesIdentifier, @RequestBody GlassesAvailability availability) {
        String availableOrNot = glassesService.changeAvailability(glassesIdentifier, availability.available());
        return ResponseEntity.ok(new Message("Óculos " + availableOrNot));
    }
}
