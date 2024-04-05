package com.lirou.store.glasses.application.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lirou.store.models.Message;
import com.lirou.store.glasses.application.service.GlassesService;
import com.lirou.store.shared.validation.identifierValidator.ValidIdentifier;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/glasses")
public class GlassesController implements GlassesAPI{

    private final GlassesService glassesService;

    public GlassesController(GlassesService glassesService) {
        this.glassesService = glassesService;
    }

    @Override
    public ResponseEntity<Page<GlassesDTO>> getGlasses(@PageableDefault(size = 24, direction = Sort.Direction.ASC, sort = { "title" }) Pageable pageable) {
        Page<GlassesDTO> glassesDTO = glassesService.getAllGlasses(pageable);
        return ResponseEntity.ok(glassesDTO);
    }
    @Override
    public ResponseEntity<GlassesDTO> findSingleGlasses(@PathVariable("identifier") @ValidIdentifier String identifier) {
        GlassesDTO glassesDTO = glassesService.getGlassesWithIdentifier(identifier);
        return ResponseEntity.ok(glassesDTO);
    }
    @Override
    public ResponseEntity<Message> postGlasses(@RequestBody @Valid GlassesDTO glassesDTO) {
        glassesService.saveNewGlasses(glassesDTO);
        return ResponseEntity.status(201).body(new Message(glassesDTO.title() + " salvo!"));
    }
    @Override
    public ResponseEntity<?> putGlasses(@RequestBody @Valid GlassesDTO glassesDTO, @PathVariable("identifier") String glassesIdentifier) {
        glassesService.editGlasses(glassesIdentifier, glassesDTO);
        return ResponseEntity.status(202).body(new Message("Atualização feita com sucesso!"));
    }
    @Override
    public ResponseEntity<?> deleteGlasses(@PathVariable("identifier") String glassesIdentifier) {
        String titleOfDeletedGlasses = glassesService.removeGlasses(glassesIdentifier);
        return ResponseEntity.ok(new Message(titleOfDeletedGlasses + " deletado!"));
    }
    @Override
    public ResponseEntity<?> changeAvailability(@PathVariable("identifier") String glassesIdentifier, @RequestBody @Valid GlassesAvailability availability)  {
        String availableOrNot = glassesService.changeAvailability(glassesIdentifier, availability.available());
        return ResponseEntity.status(202).body(new Message("Óculos " + availableOrNot));
    }
}
