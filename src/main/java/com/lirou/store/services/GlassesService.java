package com.lirou.store.services;

import com.lirou.store.DTOs.GlassesDTO;
import com.lirou.store.entities.Glasses;
import com.lirou.store.repository.GlassesRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.ws.rs.NotFoundException;

import java.util.Optional;

@Service
public class GlassesService {
    private final GlassesRepository glassesRepository;
    public GlassesService(GlassesRepository glassesRepository) {
        this.glassesRepository = glassesRepository;
    }
    // Admin:
    public Page<GlassesDTO> getAllGlasses(Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 24);
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

    public void updateGlasses(String glassesIdentifier, GlassesDTO changes) {
        Boolean alreadyExists = glassesRepository.existsByTitleAndIdentifierNotAndDeletedFalse(changes.title(), glassesIdentifier);
        if (alreadyExists) throw new DataIntegrityViolationException("Já existe um óculos com este nome.");

        Glasses glassesToEdit = glassesRepository.findByIdentifierAndDeletedFalse(glassesIdentifier);
        if (glassesToEdit == null) throw new NotFoundException("Óculos não encontrado");

        glassesToEdit.setTitle(changes.title());
        glassesToEdit.setPic(changes.pic());
        glassesToEdit.setModel(changes.model());
        glassesToEdit.setFrame(changes.frame());
        glassesToEdit.setColor(changes.color());
        glassesToEdit.setBrand(changes.brand());
        glassesToEdit.setPrice(changes.price());

        glassesRepository.save(glassesToEdit);
    }

    public String removeGlasses(String glassesIdentifier) {
        Glasses glassesToDelete = glassesRepository.findByIdentifierAndDeletedFalse(glassesIdentifier);
        if (glassesToDelete == null) throw new NotFoundException("Óculos não encontrado");

        glassesToDelete.setDeleted(true);
        glassesRepository.save(glassesToDelete);

        return glassesToDelete.getTitle();
    }

    public String changeAvailability(String glassesIdentifier, Boolean available){
        Glasses glasses = glassesRepository.findByIdentifierAndDeletedFalse(glassesIdentifier);

        glasses.setAvailable(available);
        if (available) return "disponibilizado!";
        return "indisponibilizado!";
    }

    public GlassesDTO findGlassesByIdentifier(String glassesIdentifier) {
        Glasses glasses = glassesRepository.findByIdentifierAndDeletedFalse(glassesIdentifier);
        if (glasses == null) throw new NotFoundException("Óculos não encontrado!");
        return new GlassesDTO(glasses);
    }

}
