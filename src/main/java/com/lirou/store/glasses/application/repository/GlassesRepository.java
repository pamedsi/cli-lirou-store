package com.lirou.store.glasses.application.repository;

import com.lirou.store.glasses.application.api.GlassesDTO;
import com.lirou.store.glasses.domain.Glasses;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GlassesRepository {
    Page<GlassesDTO> getAllGlasses(Pageable pageable);
    Glasses getGlasses(String identifier);
    void saveGlasses(Glasses glasses);
}
