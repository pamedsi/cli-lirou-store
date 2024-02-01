package com.lirou.store.controllers;

import lombok.extern.log4j.Log4j2;
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
import com.lirou.store.exceptions.NotFoundException;
import com.lirou.store.models.GlassesAvailability;
import com.lirou.store.models.Message;
import com.lirou.store.services.GlassesService;
import com.lirou.store.validation.identifierValidator.ValidIdentifier;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/glasses")
@Log4j2
public class GlassesController{

    private final GlassesService glassesService;

    public GlassesController(GlassesService glassesService) {
        this.glassesService = glassesService;
    }

    // Para a parte admin:
    @GetMapping
    public ResponseEntity<Page<GlassesDTO>> getGlasses(@PageableDefault(page = 0, size = 24, direction = Sort.Direction.ASC, sort = { "title" }) Pageable pageable) {
        log.info("[Inicia] GlassesService - getAllGlasses()");
        Page<GlassesDTO> glassesDTO = glassesService.getAllGlasses(pageable);
        log.info("[Finaliza] GlassesService - getAllGlasses()");
        return ResponseEntity.ok(glassesDTO);
    }
    @GetMapping("/{identifier}")
    public ResponseEntity<GlassesDTO> findSingleGlasses(@PathVariable("identifier") @ValidIdentifier String identifier) throws NotFoundException {
        log.info("[Inicia] GlassesService - findSingleGlasses()");
        GlassesDTO glassesDTO = glassesService.findGlassesByIdentifier(identifier);
        log.info("[Finaliza] GlassesService - findSingleGlasses()");
        return ResponseEntity.ok(glassesDTO);
    }
    @PostMapping
    public ResponseEntity<Message> postGlasses(@RequestBody @Valid GlassesDTO glassesDTO) {
        log.info("[Inicia] GlassesService - saveNewGlasses()");
        glassesService.saveNewGlasses(glassesDTO);
        log.info("[Finaliza] GlassesService - saveNewGlasses()");
        return ResponseEntity.status(201).body(new Message(glassesDTO.title() + " salvo!"));
    }

    @PutMapping("/{identifier}")
    public ResponseEntity<?> putGlasses(@RequestBody @Valid GlassesDTO glassesDTO, @PathVariable("identifier") String glassesIdentifier) throws NotFoundException {
        log.info("[Inicia] GlassesService - updateGlasses()");
        glassesService.updateGlasses(glassesIdentifier, glassesDTO);
        log.info("[Finaliza] GlassesService - updateGlasses()");
        return ResponseEntity.ok(new Message("Atualização feita com sucesso!"));
    }

    @DeleteMapping("/{identifier}")
    public ResponseEntity<?> deleteGlasses(@PathVariable("identifier") String glassesIdentifier) throws NotFoundException {
        log.info("[Inicia] GlassesService - removeGlasses()");
        String titleOfDeletedGlasses = glassesService.removeGlasses(glassesIdentifier);
        log.info("[Finaliza] GlassesService - removeGlasses()");
        return ResponseEntity.ok(new Message(titleOfDeletedGlasses + " deletado!"));
    }
  
    @PatchMapping("/{identifier}")
    public ResponseEntity<?> changeAvailability(@PathVariable("identifier") String glassesIdentifier, @RequestBody GlassesAvailability availability) throws NotFoundException {
        log.info("[Inicia] GlassesService - changeAvailability()");
        String availableOrNot = glassesService.changeAvailability(glassesIdentifier, availability.available());
        log.info("[Finaliza] GlassesService - changeAvailability()");
        return ResponseEntity.ok(new Message("Óculos " + availableOrNot));
    }
}
