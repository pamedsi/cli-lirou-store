package com.lirou.store.glasses.infra;

import com.lirou.store.glasses.application.api.GlassesDTO;
import com.lirou.store.glasses.application.repository.GlassesRepository;
import com.lirou.store.glasses.domain.Glasses;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Log4j2
public class GlassesInfraRepository implements GlassesRepository {
    private final GlassesJPARepository glassesJPARepository;
    @Override
    public Page<GlassesDTO> getAllGlasses(Pageable pageable) {
        log.info("[starts] GlassesInfraRepository - getAllGlasses()");
        Page<Glasses> glassesAsEntity = glassesJPARepository.findAllByDeletedFalse(pageable);
        Page<GlassesDTO> glassesDTOS = GlassesDTO.toPageDTO(glassesAsEntity);
        log.info("[ends] GlassesInfraRepository - getAllGlasses()");
        return glassesDTOS;
    }
    @Override
    public Optional<Glasses> getGlasses(String identifier) {
        log.info("[starts] GlassesInfraRepository - getGlasses()");
        Optional<Glasses> glasses = glassesJPARepository.findByIdentifierAndDeletedFalse(identifier);
        log.info("[ends] GlassesInfraRepository - getGlasses()");
        return glasses;
    }
    @Override
    public void saveGlasses(Glasses glasses) {
        log.info("[starts] GlassesInfraRepository - saveGlasses()");
        glassesJPARepository.save(glasses);
        log.info("[ends] GlassesInfraRepository - saveGlasses()");
    }
}
