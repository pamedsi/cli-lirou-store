package com.lirou.store.glasses.application.service;

import com.lirou.store.glasses.application.api.GlassesDTO;
import com.lirou.store.glasses.domain.Glasses;
import com.lirou.store.glasses.infra.GlassesInfraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class GlassesApplicationService implements GlassesService {
    private final GlassesInfraRepository glassesInfraRepository;
    public Page<GlassesDTO> getAllGlasses(Pageable pageable) {
        log.info("[starts] GlassesApplicationService - getAllGlasses()");
        Page<GlassesDTO> glassesDTOs = glassesInfraRepository.getAllGlasses(pageable);
        log.info("[ends] GlassesApplicationService - getAllGlasses()");
        return glassesDTOs;
    }
    public void saveNewGlasses(GlassesDTO glassesDTO) {
        log.info("[starts] GlassesApplicationService - saveNewGlasses()");
        Glasses newGlasses = new Glasses(glassesDTO);
        glassesInfraRepository.saveGlasses(newGlasses);
        log.info("[ends] GlassesApplicationService - saveNewGlasses()");
    }
    public void editGlasses(String glassesIdentifier, GlassesDTO changes) {
        log.info("[starts] GlassesApplicationService - editGlasses()");
        Glasses glassesToEdit = glassesInfraRepository.getGlasses(glassesIdentifier);
        updateGlassesFromDTO(glassesToEdit, changes);
        glassesInfraRepository.saveGlasses(glassesToEdit);
        log.info("[ends] GlassesApplicationService - editGlasses()");
    }
    private void updateGlassesFromDTO(Glasses glassesToEdit, GlassesDTO changes) {
        glassesToEdit.setTitle(changes.title());
        glassesToEdit.setPic(changes.pic());
        glassesToEdit.setModel(changes.model());
        glassesToEdit.setFrame(changes.frame());
        glassesToEdit.setColor(changes.color());
        glassesToEdit.setBrand(changes.brand());
        glassesToEdit.setPrice(changes.price());
    }
    public String removeGlasses(String glassesIdentifier) {
        log.info("[starts] GlassesApplicationService - removeGlasses()");
        Glasses glassesToDelete = glassesInfraRepository.getGlasses(glassesIdentifier);
        glassesToDelete.setDeleted(true);
        glassesInfraRepository.saveGlasses(glassesToDelete);
        log.info("[ends] GlassesApplicationService - removeGlasses()");
        return glassesToDelete.getTitle();
    }
    public String changeAvailability(String identifier, Boolean available) {
        log.info("[starts] GlassesApplicationService - changeAvailability()");
        Glasses glasses = glassesInfraRepository.getGlasses(identifier);
        glasses.setAvailable(available);
        if (available) return "disponibilizado!";
        log.info("[ends] GlassesApplicationService - changeAvailability()");
        return "indisponibilizado!";
    }
    public GlassesDTO getGlassesWithIdentifier(String glassesIdentifier) {
        Glasses glasses = glassesInfraRepository.getGlasses(glassesIdentifier);
        return new GlassesDTO(glasses);
    }
}
