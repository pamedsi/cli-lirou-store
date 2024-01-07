package com.lirou.store.services;

import com.lirou.store.DTOs.GlassesDTO;
import com.lirou.store.entities.Glasses;
import com.lirou.store.mapper.GlassesMapper;
import com.lirou.store.repository.GlassesRepository;

import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
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

    public void updateGlasses(String glassesIdentifier, GlassesDTO changes) {
        Glasses glassesToEdit = glassesRepository.findByIdentifierAndDeletedFalse(glassesIdentifier);
        if (glassesToEdit == null) throw new NotFoundException("Óculos não encontrado!");
        if (!someChange(glassesToEdit, changes)) return;

        glassesToEdit.setTitle(changes.title());
        glassesToEdit.setPic(changes.pic());
        glassesToEdit.setModel(changes.model());
        glassesToEdit.setFrame(changes.frame());
        glassesToEdit.setColor(changes.color());
        glassesToEdit.setBrand(changes.brand());
        glassesToEdit.setPrice(changes.price());

        glassesToEdit.setLastEditedIn(LocalDateTime.now());
        glassesRepository.save(glassesToEdit);
    }

    private Boolean someChange(Glasses glassesToEdit, GlassesDTO changes) {
        return !Objects.equals(glassesToEdit.getTitle(), changes.title()) ||
                !Objects.equals(glassesToEdit.getPic(), changes.pic()) ||
                !Objects.equals(glassesToEdit.getModel(), changes.model()) ||
                !Objects.equals(glassesToEdit.getFrame(), changes.frame()) ||
                !Objects.equals(glassesToEdit.getColor(), changes.color()) ||
                !Objects.equals(glassesToEdit.getBrand(), changes.brand()) ||
                glassesToEdit.getPrice().compareTo(changes.price()) != 0;
    }

    public String removeGlasses(String glassesIdentifier) {
        Glasses glassesToDelete = glassesRepository.findByIdentifierAndDeletedFalse(glassesIdentifier);
        if (glassesToDelete == null) throw new NotFoundException("Óculos não encontrado!");

        glassesToDelete.setDeleted(true);
        glassesRepository.save(glassesToDelete);

        return glassesToDelete.getTitle();
    }

    public String changeAvailability(String identifier, Boolean available){
        Glasses glasses = glassesRepository.findByIdentifierAndDeletedFalse(identifier);
        if (glasses == null) throw new NotFoundException("Óculos não encontrado!");
        glasses.setInStock(available);
        if (available) return "disponibilizado!";
        return "indisponibilizado!";
    }
}
