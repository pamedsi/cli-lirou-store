package com.lirou.store.glasses.application.service;

import com.lirou.store.glasses.application.api.GlassesDTO;
import com.lirou.store.glasses.domain.Glasses;
import com.lirou.store.glasses.infra.GlassesInfraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lirou.store.handler.exceptions.NotFoundException;

@Service
@RequiredArgsConstructor
@Log4j2
public class GlassesService {
    private final GlassesInfraRepository glassesInfraRepository;
    public Page<GlassesDTO> getAllGlasses(Pageable pageable) {
        log.info("[starts] GlassesInfraRepository - getAllGlasses()");
        Page<GlassesDTO> glassesDTOs = glassesInfraRepository.getAllGlasses(pageable);
        log.info("[ends] GlassesInfraRepository - getAllGlasses()");
        return glassesDTOs;
    }
    public void saveNewGlasses(GlassesDTO glassesDTO) {
        log.info("[starts] GlassesService - saveNewGlasses()");
        Glasses newGlasses = new Glasses(glassesDTO);
        glassesInfraRepository.saveGlasses(newGlasses);
        log.info("[ends] GlassesService - saveNewGlasses()");
    }
    public void editGlasses(String glassesIdentifier, GlassesDTO changes) throws NotFoundException {
        log.info("[starts] GlassesService - editGlasses()");
        Glasses glassesToEdit = checkIfGlassesExistsAndReturnsIt(glassesIdentifier);
        updateGlassesFromDTO(glassesToEdit, changes);
        glassesInfraRepository.saveGlasses(glassesToEdit);
        log.info("[ends] GlassesService - editGlasses()");
    }
    private void updateGlassesFromDTO(Glasses glassesToEdit, GlassesDTO changes){
        glassesToEdit.setTitle(changes.title());
        glassesToEdit.setPic(changes.pic());
        glassesToEdit.setModel(changes.model());
        glassesToEdit.setFrame(changes.frame());
        glassesToEdit.setColor(changes.color());
        glassesToEdit.setBrand(changes.brand());
        glassesToEdit.setPrice(changes.price());
    }
    public String removeGlasses(String glassesIdentifier) throws NotFoundException {
        log.info("[starts] GlassesService - removeGlasses()");
        Glasses glassesToDelete = checkIfGlassesExistsAndReturnsIt(glassesIdentifier);
        glassesToDelete.setDeleted(true);
        glassesInfraRepository.saveGlasses(glassesToDelete);
        log.info("[ends] GlassesService - removeGlasses()");
        return glassesToDelete.getTitle();
    }
    public String changeAvailability(String identifier, Boolean available) throws NotFoundException {
        log.info("[starts] GlassesService - changeAvailability()");
        Glasses glasses = checkIfGlassesExistsAndReturnsIt(identifier);
        glasses.setAvailable(available);
        if (available) return "disponibilizado!";
        log.info("[ends] GlassesService - changeAvailability()");
        return "indisponibilizado!";
    }
    public GlassesDTO getGlassesWithIdentifier(String glassesIdentifier) throws NotFoundException {
        Glasses glasses = checkIfGlassesExistsAndReturnsIt(glassesIdentifier);
        return new GlassesDTO(glasses);
    }
    private Glasses checkIfGlassesExistsAndReturnsIt(String identifier) throws NotFoundException {
        return glassesInfraRepository.getGlasses(identifier)
                .orElseThrow(() -> new NotFoundException("Óculos não encontrado!"));
    }
}
