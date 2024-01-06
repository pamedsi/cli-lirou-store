package com.lirou.store.controllers;

import com.lirou.store.DTOs.GlassesDTO;
import com.lirou.store.models.BaseController;
import com.lirou.store.models.Message;
import com.lirou.store.services.GlassesService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/glasses")
public class GlassesController extends BaseController {

    private final GlassesService glassesService;

    public GlassesController(GlassesService glassesService) {
        this.glassesService = glassesService;
    }

    // Para a parte admin:
    @GetMapping
    public ResponseEntity<?> getGlasses() {
        List<GlassesDTO> glassesDTO = glassesService.getAllGlasses();
        return ResponseEntity.ok(glassesDTO);
    }
    @PostMapping
    public ResponseEntity<?> postGlasses(@RequestBody GlassesDTO glassesDTO) {
        glassesService.saveNewGlasses(glassesDTO);
        return ResponseEntity.status(201).body(new Message(glassesDTO.title() + "salvo!"));
    }

    @PutMapping("/{identifier}")
    public ResponseEntity<?> putGlasses(@RequestBody @Valid GlassesDTO glassesDTO, @PathVariable("identifier") String glassesIdentifier) {
        glassesService.updateGlasses(glassesIdentifier, glassesDTO);
        return ResponseEntity.ok(new Message("Atualização feita com sucesso!"));
    }

    @DeleteMapping("/{identifier}")
    public ResponseEntity<?> deleteGlasses(@PathVariable("identifier") String glassesIdentifier) {
        String titleOfDeletedGlasses = glassesService.removeGlasses(glassesIdentifier);
        return ResponseEntity.ok(new Message(titleOfDeletedGlasses + "deletado!"));
    }

    @PatchMapping("/{identifier}")
    public ResponseEntity<?> changeAvailability(@PathVariable("identifier") String glassesIdentifier, @RequestBody Boolean availability) {
        String available = glassesService.changeAvailability(glassesIdentifier, availability);
        return ResponseEntity.ok(new Message("Óculos " + available));
    }

}
