package com.lirou.store.services;

import com.lirou.store.DTOs.GlassesDTO;
import com.lirou.store.entities.Glasses;
import com.lirou.store.mapper.GlassesMapper;
import com.lirou.store.repository.GlassesRepository;

import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GlassesService {
    private GlassesRepository glassesRepository;
    private GlassesMapper glassesMapper;

    public GlassesService(GlassesRepository glassesRepository, GlassesMapper glassesMapper) {
        this.glassesRepository = glassesRepository;
        this.glassesMapper = glassesMapper;
    }
    // Admin:
    public List<GlassesDTO> getAllGlasses() {
        List<Glasses> glassesAsEntity = glassesRepository.findAllByDeletedFalse();
        return glassesMapper.severalToDTO(glassesAsEntity);
    }

    public void saveNewGlasses(GlassesDTO glassesDTO) {
        Glasses newGlasses = new Glasses(glassesDTO);
        glassesRepository.save(newGlasses);
    }

    public void updateGlasses(GlassesDTO changes) {
        Glasses glassesToEdit = glassesRepository.findByIdentifierAndDeletedFalse(changes.identifier());
        if (glassesToEdit == null) throw new NotFoundException("Óculos não encontrado!");

        glassesToEdit.setLastEditedIn(LocalDateTime.now());
        glassesRepository.save(glassesToEdit);
    }

    public String removeGlasses(String glassesIdentifier) {
        Glasses glassesToDelete = glassesRepository.findByIdentifierAndDeletedFalse(glassesIdentifier);
        if (glassesToDelete == null) throw new NotFoundException("Óculos não encontrado!");

        glassesToDelete.setDeleted(true);
        glassesRepository.save(glassesToDelete);

        return glassesToDelete.getTitle();
    }
}
