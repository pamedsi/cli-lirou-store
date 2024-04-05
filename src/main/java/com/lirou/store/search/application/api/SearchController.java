package com.lirou.store.search.application.api;

import com.lirou.store.glasses.application.api.GlassesDTO;
import com.lirou.store.search.application.service.SearchApplicationService;

import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Log4j2
public class SearchController {

    private final SearchApplicationService searchApplicationService;

    @GetMapping
    public ResponseEntity<?> searchProduct(@RequestParam(value = "query", required = false, defaultValue = "") String query, @PageableDefault(size = 24) Pageable pageable) {
        log.info("[Inicia] SearchService - searchProductWithQuery()");
        Page<GlassesDTO> result = searchApplicationService.searchProductWithQuery(query, pageable);
        log.info("[Finaliza] SearchService - searchProductWithQuery()");
        return ResponseEntity.ok(result);
    }
}
