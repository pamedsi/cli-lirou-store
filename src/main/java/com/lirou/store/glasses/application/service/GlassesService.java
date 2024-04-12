package com.lirou.store.glasses.application.service;

import com.lirou.store.glasses.application.api.GlassesDTO;

import com.lirou.store.glasses.application.api.GlassesRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GlassesService {
    Page<GlassesDTO> getAllGlasses(Pageable pageable);
    void saveNewGlasses(GlassesRequestDTO glassesDTO);
    void editGlasses(String glassesIdentifier, GlassesRequestDTO changes);
    String removeGlasses(String glassesIdentifier);
    String changeAvailability(String identifier, Boolean available);
    GlassesDTO getGlassesWithIdentifier(String glassesIdentifier);
}
