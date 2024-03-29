package com.lirou.store.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lirou.store.domain.DTOs.GlassesDTO;
import com.lirou.store.exceptions.NameExisteInDatabaseException;
import com.lirou.store.exceptions.NotFoundException;
import com.lirou.store.models.GlassesAvailability;
import com.lirou.store.models.Message;
import com.lirou.store.services.GlassesService;
import com.lirou.store.validation.identifierValidator.ValidIdentifier;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/glasses")
public class GlassesController{

    private final GlassesService glassesService;

    public GlassesController(GlassesService glassesService) {
        this.glassesService = glassesService;
    }

    // Para a parte admin:
    @GetMapping
    public ResponseEntity<Page<GlassesDTO>> getGlasses(@PageableDefault(size = 24, direction = Sort.Direction.ASC, sort = { "title" }) Pageable pageable) {
        Page<GlassesDTO> glassesDTO = glassesService.getAllGlasses(pageable);
        return ResponseEntity.ok(glassesDTO);
    }
    @GetMapping("/{identifier}")
    public ResponseEntity<GlassesDTO> findSingleGlasses(@PathVariable("identifier") @ValidIdentifier String identifier) throws NotFoundException {
        GlassesDTO glassesDTO = glassesService.findGlassesByIdentifier(identifier);
        return ResponseEntity.ok(glassesDTO);
    }
    @PostMapping
    public ResponseEntity<Message> postGlasses(@RequestBody @Valid GlassesDTO glassesDTO) throws NameExisteInDatabaseException {
        glassesService.saveNewGlasses(glassesDTO);
        return ResponseEntity.status(201).body(new Message(glassesDTO.title() + " salvo!"));
    }

    @PutMapping("/{identifier}")
    public ResponseEntity<?> putGlasses(@RequestBody @Valid GlassesDTO glassesDTO, @PathVariable("identifier") String glassesIdentifier) throws NotFoundException {
        glassesService.updateGlasses(glassesIdentifier, glassesDTO);
        return ResponseEntity.ok(new Message("Atualização feita com sucesso!"));
    }

    @DeleteMapping("/{identifier}")
    public ResponseEntity<?> deleteGlasses(@PathVariable("identifier") String glassesIdentifier) throws NotFoundException {
        String titleOfDeletedGlasses = glassesService.removeGlasses(glassesIdentifier);
        return ResponseEntity.ok(new Message(titleOfDeletedGlasses + " deletado!"));
    }
  
    @PatchMapping("/{identifier}")
    public ResponseEntity<?> changeAvailability(@PathVariable("identifier") String glassesIdentifier, @RequestBody @Valid GlassesAvailability availability) throws NotFoundException {
        String availableOrNot = glassesService.changeAvailability(glassesIdentifier, availability.available());
        return ResponseEntity.ok(new Message("Óculos " + availableOrNot));
    }
}
