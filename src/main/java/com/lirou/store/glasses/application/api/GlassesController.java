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
import com.lirou.store.glasses.application.service.GlassesApplicationService;
import com.lirou.store.shared.validation.identifierValidator.ValidIdentifier;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/glasses")
public class GlassesController implements GlassesAPI{

    private final GlassesApplicationService glassesApplicationService;

    public GlassesController(GlassesApplicationService glassesApplicationService) {
        this.glassesApplicationService = glassesApplicationService;
    }

    @Override
    public ResponseEntity<Page<GlassesDTO>> getGlasses(@PageableDefault(size = 24, direction = Sort.Direction.ASC, sort = { "title" }) Pageable pageable) {
        Page<GlassesDTO> glassesDTO = glassesApplicationService.getAllGlasses(pageable);
        return ResponseEntity.ok(glassesDTO);
    }
    @Override
    public ResponseEntity<GlassesDTO> findSingleGlasses(@PathVariable("identifier") @ValidIdentifier String identifier) {
        GlassesDTO glassesDTO = glassesApplicationService.getGlassesWithIdentifier(identifier);
        return ResponseEntity.ok(glassesDTO);
    }
    @Override
    public ResponseEntity<Message> postGlasses(@RequestBody @Valid GlassesDTO glassesDTO) {
        glassesApplicationService.saveNewGlasses(glassesDTO);
        return ResponseEntity.status(201).body(new Message(glassesDTO.title() + " salvo!"));
    }
    @Override
    public ResponseEntity<?> putGlasses(@RequestBody @Valid GlassesDTO glassesDTO, @PathVariable("identifier") String glassesIdentifier) {
        glassesApplicationService.editGlasses(glassesIdentifier, glassesDTO);
        return ResponseEntity.status(202).body(new Message("Atualização feita com sucesso!"));
    }
    @Override
    public ResponseEntity<?> deleteGlasses(@PathVariable("identifier") String glassesIdentifier) {
        String titleOfDeletedGlasses = glassesApplicationService.removeGlasses(glassesIdentifier);
        return ResponseEntity.ok(new Message(titleOfDeletedGlasses + " deletado!"));
    }
    @Override
    public ResponseEntity<?> changeAvailability(@PathVariable("identifier") String glassesIdentifier, @RequestBody @Valid GlassesAvailability availability)  {
        String availableOrNot = glassesApplicationService.changeAvailability(glassesIdentifier, availability.available());
        return ResponseEntity.status(202).body(new Message("Óculos " + availableOrNot));
    }
}
