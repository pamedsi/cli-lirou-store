package com.lirou.store.services;

import com.lirou.store.DTOs.GlassesDTO;
import com.lirou.store.entities.Glasses;
import com.lirou.store.mapper.glasses.GlassesMapper;
import com.lirou.store.repository.GlassesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GlassesService {
    private GlassesRepository glassesRepository;
    private GlassesMapper glassesMapper;

    public GlassesService(GlassesRepository glassesRepository, GlassesMapper glassesMapper) {
        this.glassesRepository = glassesRepository;
        this.glassesMapper = glassesMapper;
    }

    public List<GlassesDTO> getAllGlasses() {
        List<Glasses> glassesAsEntity = glassesRepository.findAllByDeletedFalse();
        return glassesMapper.severalToDTO(glassesAsEntity);
    }
}
