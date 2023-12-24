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

    public List<String> updateGlasses(GlassesDTO changes) {
        Glasses glassesToEdit = glassesRepository.findByIdentifierAndDeletedFalse(changes.identifier());
        if (glassesToEdit == null) throw new NotFoundException("Óculos não encontrado!");

        List<String> changesForResponse = new ArrayList<>();

        if (!Objects.equals(glassesToEdit.getTitle(), changes.title())) {
            glassesToEdit.setTitle(changes.title());
            changesForResponse.add(STR."Título modificado! Agora se chama: \{changes.title()}");
        }
        if (!Objects.equals(glassesToEdit.getPic(), changes.pic())) {
            glassesToEdit.setPic(changes.pic());
            changesForResponse.add(STR."Foto modificada! Agora a URL é: \{changes.pic()}");
        }
        if (!Objects.equals(glassesToEdit.getFrame(), changes.frame())) {
            glassesToEdit.setFrame(changes.frame());
            changesForResponse.add(STR."Armação atualizada, agora é: \{changes.frame()}");
        }
        if (!Objects.equals(glassesToEdit.getColor(), changes.color())) {
            glassesToEdit.setColor(changes.color());
            changesForResponse.add(STR."Cor atualizada, agora é: \{changes.color()}");
        }
        if (!Objects.equals(glassesToEdit.getModel(), changes.model())) {
            glassesToEdit.setModel(changes.model());
            changesForResponse.add(STR."Modelo atualizado, agora é: \{changes.model()}");
        }
        if (glassesToEdit.getPrice().compareTo(changes.price()) != 0) {
            glassesToEdit.setPrice(changes.price());
            changesForResponse.add(STR."Preço alterado! Agora custa: \{changes.price()}");
        }
        if (glassesToEdit.getInStock() != changes.inStock()) {
            glassesToEdit.setInStock(changes.inStock());
            if (changes.inStock()) changesForResponse.add(STR."\{changes.title()} disponibilizado!");
            else changesForResponse.add(STR."\{changes.title()} indisponibilizado!");
        }

        if (changesForResponse.isEmpty()) throw new BadRequestException("Nenhuma mudança solicitada é diferente dos dados já presentes!");
        glassesToEdit.setLastEditedIn(LocalDateTime.now());
        glassesRepository.save(glassesToEdit);
        return changesForResponse;
    }

    public String removeGlasses(String glassesIdentifier) {
        Glasses glassesToDelete = glassesRepository.findByIdentifierAndDeletedFalse(glassesIdentifier);
        if (glassesToDelete == null) throw new NotFoundException("Óculos não encontrado!");

        glassesToDelete.setDeleted(true);
        glassesRepository.save(glassesToDelete);

        return glassesToDelete.getTitle();
    }
}
