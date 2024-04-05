package com.lirou.store.services;

import com.lirou.store.glasses.application.api.GlassesDTO;
import com.lirou.store.glasses.domain.Glasses;
import com.lirou.store.repository.searches.GlassesSearchRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class SearchService {

    private final GlassesSearchRepository searchRepository;

    public Page<GlassesDTO> searchProductWithQuery(String query, Pageable pageable) {
        log.info("[Inicia] SearchRepository - searchGlasses()");
        Page<Glasses> glassesList = searchRepository.searchGlasses(query, pageable);
        // Implementar buscas a outros tipos de produtos aqui também.
        log.info("[Finaliza] SearchRepository - searchGlasses()");
        return GlassesDTO.toPageDTO(glassesList);
    }
}
