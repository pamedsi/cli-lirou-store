package com.lirou.store.services;

import com.lirou.store.domain.DTOs.GlassesDTO;
import com.lirou.store.domain.entities.Glasses;
import com.lirou.store.repository.SearchRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class SearchService {

    private final SearchRepository searchRepository;

    public Page<GlassesDTO> searchGlassesWithQuery(String query, Pageable pageable) {
        log.info("[Inicia] SearchRepository - searchGlasses()");
        Page<Glasses> glassesList = searchRepository.searchGlasses(query, pageable);
        log.info("[Finaliza] SearchRepository - searchGlasses()");
        return GlassesDTO.toPageDTO(glassesList);
    }
//    public List<GlassesDTO> searchGlassesWithQuery(String query, Pageable pageable) {
//        log.info("[Inicia] SearchRepository - searchGlasses()");
//        List<Glasses> glassesList = searchRepository.searchGlasses(query, pageable);
//        log.info("[Finaliza] SearchRepository - searchGlasses()");
//        return GlassesDTO.severalToDTO(glassesList);
//    }
}
