package com.lirou.store.glasses.application.service;

import com.lirou.store.glasses.application.api.GlassesDTO;
import com.lirou.store.glasses.application.api.GlassesRequestDTO;
import com.lirou.store.glasses.application.repository.GlassesRepository;
import com.lirou.store.glasses.domain.Glasses;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class GlassesApplicationService implements GlassesService {
    private final GlassesRepository glassesRepository;
    public Page<GlassesDTO> getAllGlasses(Pageable pageable) {
        log.info("[starts] GlassesApplicationService - getAllGlasses()");
        Page<GlassesDTO> glassesDTOs = glassesRepository.getAllGlasses(pageable);
        log.info("[ends] GlassesApplicationService - getAllGlasses()");
        return glassesDTOs;
    }
    public void saveNewGlasses(GlassesRequestDTO glassesDTO) {
        log.info("[starts] GlassesApplicationService - saveNewGlasses()");
        Glasses newGlasses = new Glasses(glassesDTO);
        glassesRepository.saveGlasses(newGlasses);
        log.info("[ends] GlassesApplicationService - saveNewGlasses()");
    }
    public void editGlasses(String glassesIdentifier, GlassesRequestDTO changes) {
        log.info("[starts] GlassesApplicationService - editGlasses()");
        Glasses glassesToEdit = glassesRepository.getGlasses(glassesIdentifier);
        glassesToEdit.updateGlassesFromDTO(glassesToEdit, changes);
        glassesRepository.saveGlasses(glassesToEdit);
        log.info("[ends] GlassesApplicationService - editGlasses()");
    } // b368afab-913e-4042-b906-94d671f68f43

    public String removeGlasses(String glassesIdentifier) {
        log.info("[starts] GlassesApplicationService - removeGlasses()");
        Glasses glassesToDelete = glassesRepository.getGlasses(glassesIdentifier);
        glassesToDelete.setDeleted(true);
        glassesRepository.saveGlasses(glassesToDelete);
        log.info("[ends] GlassesApplicationService - removeGlasses()");
        return glassesToDelete.getTitle();
    }
    public String changeAvailability(String identifier, Boolean available) {
        log.info("[starts] GlassesApplicationService - changeAvailability()");
        Glasses glasses = glassesRepository.getGlasses(identifier);
        glasses.setAvailable(available);
        log.info("[ends] GlassesApplicationService - changeAvailability()");
        return available ? "disponibilizado!" : "indisponibilizado!";
    }
    public GlassesDTO getGlassesWithIdentifier(String glassesIdentifier) {
        Glasses glasses = glassesRepository.getGlasses(glassesIdentifier);
        return new GlassesDTO(glasses);
    }
}
