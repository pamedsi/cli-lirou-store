package com.lirou.store.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lirou.store.DTOs.GlassesDTO;
import com.lirou.store.entities.Glasses;
import com.lirou.store.exceptions.NotFoundException;
import com.lirou.store.repository.GlassesRepository;

@Service
public class GlassesService {
    private GlassesRepository glassesRepository;
    public GlassesService(GlassesRepository glassesRepository) {
        this.glassesRepository = glassesRepository;
    }
    // Admin:
    public List<GlassesDTO> getAllGlasses() {
        List<Glasses> glassesAsEntity = glassesRepository.findAllByDeletedFalse();
        return GlassesDTO.severalToDTO(glassesAsEntity);
    }

    public void saveNewGlasses(GlassesDTO glassesDTO) {
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
        glasses.setInStock(available);
        if (available) return "disponibilizado!";
        return "indisponibilizado!";
    }
}
