package com.lirou.store.glasses.application.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lirou.store.models.Message;
import com.lirou.store.glasses.application.service.GlassesApplicationService;
import com.lirou.store.shared.validation.identifierValidator.ValidIdentifier;

import jakarta.validation.Valid;


@RestController
@Log4j2
@RequiredArgsConstructor
public class GlassesController implements GlassesAPI {

    private final GlassesApplicationService glassesApplicationService;

    @Override
    public ResponseEntity<Page<GlassesDTO>> getGlasses(@PageableDefault(size = 24, direction = Sort.Direction.ASC, sort = { "title" }) Pageable pageable) {
        log.info("[starts] GlassesController - getGlasses()");
        Page<GlassesDTO> glassesDTO = glassesApplicationService.getAllGlasses(pageable);
        log.info("[ends] GlassesController - getGlasses()");
        return ResponseEntity.ok(glassesDTO);
    }
    @Override
    public ResponseEntity<GlassesDTO> findSingleGlasses(@PathVariable("identifier") @ValidIdentifier String identifier) {
        log.info("[starts] GlassesController - findSingleGlasses()");
        GlassesDTO glassesDTO = glassesApplicationService.getGlassesWithIdentifier(identifier);
        log.info("[ends] GlassesController - findSingleGlasses()");
        return ResponseEntity.ok(glassesDTO);
    }
    @Override
    public ResponseEntity<Message> postGlasses(@RequestBody @Valid GlassesDTO glassesDTO) {
        log.info("[starts] GlassesController - postGlasses()");
        glassesApplicationService.saveNewGlasses(glassesDTO);
        log.info("[ends] GlassesController - postGlasses()");
        return ResponseEntity.status(201).body(new Message(glassesDTO.title() + " salvo!"));
    }
    @Override
    public ResponseEntity<?> putGlasses(@RequestBody @Valid GlassesDTO glassesDTO, @PathVariable("identifier") String glassesIdentifier) {
        log.info("[ends] GlassesController - putGlasses()");
        glassesApplicationService.editGlasses(glassesIdentifier, glassesDTO);
        log.info("[ends] GlassesController - putGlasses()");
        return ResponseEntity.status(202).body(new Message("Atualização feita com sucesso!"));
    }
    @Override
    public ResponseEntity<?> deleteGlasses(@PathVariable("identifier") String glassesIdentifier) {
        log.info("[starts] GlassesController - deleteGlasses()");
        String titleOfDeletedGlasses = glassesApplicationService.removeGlasses(glassesIdentifier);
        log.info("[ends] GlassesController - deleteGlasses()");
        return ResponseEntity.ok(new Message(titleOfDeletedGlasses + " deletado!"));
    }
    @Override
    public ResponseEntity<?> changeAvailability(@PathVariable("identifier") String glassesIdentifier, @RequestBody @Valid GlassesAvailability availability)  {
        log.info("[starts] GlassesController - changeAvailability()");
        String availableOrNot = glassesApplicationService.changeAvailability(glassesIdentifier, availability.available());
        log.info("[ends] GlassesController - changeAvailability()");
        return ResponseEntity.status(202).body(new Message("Óculos " + availableOrNot));
    }
}
