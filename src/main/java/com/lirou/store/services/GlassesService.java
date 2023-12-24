package com.lirou.store.services;

import com.lirou.store.mapper.glasses.GlassesMapper;
import com.lirou.store.repository.GlassesRepository;
import org.springframework.stereotype.Service;

@Service
public class GlassesService {
    private GlassesRepository glassesRepository;
    private GlassesMapper glassesMapper;

    public GlassesService(GlassesRepository glassesRepository, GlassesMapper glassesMapper) {
        this.glassesRepository = glassesRepository;
        this.glassesMapper = glassesMapper;
    }
}
