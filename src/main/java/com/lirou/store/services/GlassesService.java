package com.lirou.store.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lirou.store.domain.DTOs.GlassesDTO;
import com.lirou.store.domain.entities.Glasses;
import com.lirou.store.exceptions.NameExisteInDatabaseException;
import com.lirou.store.exceptions.NotFoundException;
import com.lirou.store.repository.GlassesRepository;

import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
public class GlassesService {
    private final GlassesRepository glassesRepository;
    public GlassesService(GlassesRepository glassesRepository) {
        this.glassesRepository = glassesRepository;
    }
    // Admin:
    public Page<GlassesDTO> getAllGlasses(Pageable pageable) {
        log.info("[Inicia] GlassesRepository - findAllByDeletedFalse()");
        Page<Glasses> glassesAsEntity = glassesRepository.findAllByDeletedFalse(pageable);
        log.info("[Finaliza] GlassesRepository - findAllByDeletedFalse()");
        return GlassesDTO.toPageDTO(glassesAsEntity);
    }

    public void saveNewGlasses(GlassesDTO glassesDTO) throws NameExisteInDatabaseException {
        log.info("[Inicia] GlassesRepository - existsByTitleAndDeletedFalse()");
        if (glassesRepository.existsByTitleAndDeletedFalse(glassesDTO.title())) {
            throw new NameExisteInDatabaseException("Já existe um óculos com este nome.");
        }
        log.info("[Finaliza] GlassesRepository - existsByTitleAndDeletedFalse()");
        Glasses newGlasses = new Glasses(glassesDTO);
        log.info("[Inicia] GlassesRepository - save()");
        glassesRepository.save(newGlasses);
        log.info("[Finaliza] GlassesRepository - save()");
    }

    public void updateGlasses(String glassesIdentifier, GlassesDTO changes) throws NotFoundException {
        log.info("[Inicia] GlassesRepository - findByIdentifierAndDeletedFalse()");
        Glasses glassesToEdit = glassesRepository.findByIdentifierAndDeletedFalse(glassesIdentifier)
        		.orElseThrow(() -> new NotFoundException("Óculos não encontrado!"));
        log.info("[Finaliza] GlassesRepository - findByIdentifierAndDeletedFalse()");
        glassesToEdit.setTitle(changes.title());
        glassesToEdit.setPic(changes.pic());
        glassesToEdit.setModel(changes.model());
        glassesToEdit.setFrame(changes.frame());
        glassesToEdit.setColor(changes.color());
        glassesToEdit.setBrand(changes.brand());
        glassesToEdit.setPrice(changes.price());

        log.info("[Inicia] GlassesRepository - save()");
        glassesRepository.save(glassesToEdit);
        log.info("[Finaliza] GlassesRepository - save()");
    }

    public String removeGlasses(String glassesIdentifier) throws NotFoundException {
        log.info("[Inicia] GlassesRepository - findByIdentifierAndDeletedFalse()");
        Glasses glassesToDelete = glassesRepository.findByIdentifierAndDeletedFalse(glassesIdentifier)
        		.orElseThrow(() -> new NotFoundException("Óculos não encontrado!"));
        log.info("[Finaliza] GlassesRepository - findByIdentifierAndDeletedFalse()");

        glassesToDelete.setDeleted(true);

        log.info("[Inicia] GlassesRepository - save()");
        glassesRepository.save(glassesToDelete);
        log.info("[Finaliza] GlassesRepository - save()");

        return glassesToDelete.getTitle();
    }

    public String changeAvailability(String identifier, Boolean available) throws NotFoundException{
        log.info("[Inicia] GlassesRepository - findByIdentifierAndDeletedFalse()");
        Glasses glasses = glassesRepository.findByIdentifierAndDeletedFalse(identifier)
        		.orElseThrow(() -> new NotFoundException("Óculos não encontrado!"));
        log.info("[Finaliza] GlassesRepository - findByIdentifierAndDeletedFalse()");
        glasses.setAvailable(available);

        log.info("[Inicia] GlassesRepository - save()");
        glassesRepository.save(glasses);
        log.info("[Finaliza] GlassesRepository - save()");

        if (available) return "disponibilizado!";
        return "indisponibilizado!";
    }

    public GlassesDTO findGlassesByIdentifier(String glassesIdentifier) throws NotFoundException {
        log.info("[Inicia] GlassesRepository - findByIdentifierAndDeletedFalse()");
        Glasses glasses = glassesRepository.findByIdentifierAndDeletedFalse(glassesIdentifier)
                .orElseThrow(() -> new NotFoundException("Óculos não encontrado!"));
        log.info("[Finaliza] GlassesRepository - findByIdentifierAndDeletedFalse()");
        return new GlassesDTO(glasses);
    }

}
