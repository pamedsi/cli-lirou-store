package com.lirou.store.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lirou.store.domain.DTOs.GlassesDTO;
import com.lirou.store.domain.entities.Glasses;

import com.lirou.store.exceptions.NotFoundException;
import com.lirou.store.repository.GlassesRepository;

@Service
public class GlassesService {
    private final GlassesRepository glassesRepository;
    public GlassesService(GlassesRepository glassesRepository) {
        this.glassesRepository = glassesRepository;
    }
    // Admin:
    public Page<GlassesDTO> getAllGlasses(Pageable pageable) {
        Page<Glasses> glassesAsEntity = glassesRepository.findAllByDeletedFalse(pageable);
        return GlassesDTO.toPageDTO(glassesAsEntity);
    }

    public void saveNewGlasses(GlassesDTO glassesDTO) {
        if (glassesRepository.existsByTitleAndDeletedFalse(glassesDTO.title())) {
            throw new DataIntegrityViolationException("Já existe um óculos com este nome.");
        }
        Glasses newGlasses = new Glasses(glassesDTO);
        glassesRepository.save(newGlasses);
    }

    public void updateGlasses(String glassesIdentifier, GlassesDTO changes) throws NotFoundException {
        Glasses glassesToEdit = glassesRepository.findByIdentifierAndDeletedFalse(glassesIdentifier)
        		.orElseThrow(() -> new NotFoundException("Óculos não encontrado!"));
        glassesToEdit.setTitle(changes.title());
        glassesToEdit.setPic(changes.pic());
        glassesToEdit.setModel(changes.model());
        glassesToEdit.setFrame(changes.frame());
        glassesToEdit.setColor(changes.color());
        glassesToEdit.setBrand(changes.brand());
        glassesToEdit.setPrice(changes.price());

        glassesRepository.save(glassesToEdit);
    }

    public String removeGlasses(String glassesIdentifier) throws NotFoundException {
        Glasses glassesToDelete = glassesRepository.findByIdentifierAndDeletedFalse(glassesIdentifier)
        		.orElseThrow(() -> new NotFoundException("Óculos não encontrado!"));
        glassesToDelete.setDeleted(true);
        glassesRepository.save(glassesToDelete);

        return glassesToDelete.getTitle();
    }

    public String changeAvailability(String identifier, Boolean available) throws NotFoundException{
        Glasses glasses = glassesRepository.findByIdentifierAndDeletedFalse(identifier)
        		.orElseThrow(() -> new NotFoundException("Óculos não encontrado!"));
        glasses.setAvailable(available);
        if (available) return "disponibilizado!";
        return "indisponibilizado!";
    }

    public GlassesDTO findGlassesByIdentifier(String glassesIdentifier) throws NotFoundException {
        Glasses glasses = glassesRepository.findByIdentifierAndDeletedFalse(glassesIdentifier)
        		.orElseThrow(() -> new NotFoundException("Óculos não encontrado!"));
        return new GlassesDTO(glasses);
    }

}
