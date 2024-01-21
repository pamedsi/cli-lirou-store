package com.lirou.store.services;

import com.lirou.store.DTOs.GlassesDTO;
import com.lirou.store.entities.Glasses;
import com.lirou.store.repository.GlassesRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

import static com.lirou.store.validation.IdentifierValidator.validIdentifier;

@Service
public class GlassesService {
    private final GlassesRepository glassesRepository;
    public GlassesService(GlassesRepository glassesRepository) {
        this.glassesRepository = glassesRepository;
    }
    // Admin:
    public Page<GlassesDTO> getAllGlasses() {
        Page<Glasses> glassesAsEntity = glassesRepository.findAllByDeletedFalse(PageRequest.of(0, 10));
        return GlassesDTO.toPageDTO(glassesAsEntity);
    }

    public void saveNewGlasses(GlassesDTO glassesDTO) {
        Glasses newGlasses = new Glasses(glassesDTO);
        glassesRepository.save(newGlasses);
    }

    public void updateGlasses(String glassesIdentifier, GlassesDTO changes) {
        if (!validIdentifier(glassesIdentifier)) throw new BadRequestException("Identificador inválido!");
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
        if (!validIdentifier(glassesIdentifier)) throw new BadRequestException("Identificador inválido!");
        Glasses glassesToDelete = glassesRepository.findByIdentifierAndDeletedFalse(glassesIdentifier);
        if (glassesToDelete == null) throw new NotFoundException("Óculos não encontrado");

        glassesToDelete.setDeleted(true);
        glassesRepository.save(glassesToDelete);

        return glassesToDelete.getTitle();
    }

    public String changeAvailability(String glassesIdentifier, Boolean available){
        Glasses glasses = glassesRepository.findByIdentifierAndDeletedFalse(glassesIdentifier);
        if (!validIdentifier(glassesIdentifier)) throw new BadRequestException("Identificador inválido!");

        glasses.setAvailable(available);
        if (available) return "disponibilizado!";
        return "indisponibilizado!";
    }

    public GlassesDTO findGlassesByIdentifier(String glassesIdentifier) {
        if (!validIdentifier(glassesIdentifier)) throw new BadRequestException("Identificador inválido!");

        Glasses glasses = glassesRepository.findByIdentifierAndDeletedFalse(glassesIdentifier);
        if (glasses == null) throw new NotFoundException("Óculos não encontrado!");
        return new GlassesDTO(glasses);
    }

}
