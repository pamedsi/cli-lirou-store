package com.lirou.store.controllers;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lirou.store.domain.DTOs.GlassesDTO;
import com.lirou.store.services.SearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Log4j2
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/{query}")
    public ResponseEntity<?> searchProduct(@PathVariable String query, @PageableDefault(page = 0, size = 24) Pageable pageable){
        log.info("[Inicia] SearchService - searchGlassesWithQuery()");
        Page<GlassesDTO> result = searchService.searchGlassesWithQuery(query, pageable);
        log.info("[Finaliza] SearchService - searchGlassesWithQuery()");
        return ResponseEntity.ok(result);
    }
}
