package com.lirou.store.services;

import com.lirou.store.DTOs.GlassesDTO;
import com.lirou.store.entities.Glasses;
import com.lirou.store.repository.GlassesRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.NotFoundException;
import java.util.List;

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

    public void updateGlasses(String glassesIdentifier, GlassesDTO changes) {
        if (glassesIdentifier.length() != 36) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Identificador inválido!");
        Glasses glassesToEdit = glassesRepository.findByIdentifierAndDeletedFalse(glassesIdentifier);
        if (glassesToEdit == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Identificador inválido!");

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
        if (glassesIdentifier.length() != 36) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Identificador inválido!");
        Glasses glassesToDelete = glassesRepository.findByIdentifierAndDeletedFalse(glassesIdentifier);
        if (glassesToDelete == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Identificador inválido!");

        glassesToDelete.setDeleted(true);
        glassesRepository.save(glassesToDelete);

        return glassesToDelete.getTitle();
    }

    public String changeAvailability(String identifier, Boolean available){
        Glasses glasses = glassesRepository.findByIdentifierAndDeletedFalse(identifier);
        if (glasses == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Identificador inválido!");
        glasses.setAvailable(available);
        if (available) return "disponibilizado!";
        return "indisponibilizado!";
    }

    public GlassesDTO findGlassesByIdentifier(String glassesIdentifier) {
        if (glassesIdentifier.length() != 36) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Identificador inválido!");

        Glasses glasses = glassesRepository.findByIdentifierAndDeletedFalse(glassesIdentifier);
        if (glasses == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Óculos não encontrado!");
        return new GlassesDTO(glasses);
    }

}
