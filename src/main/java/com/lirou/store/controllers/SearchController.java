package com.lirou.store.controllers;

import com.lirou.store.domain.DTOs.GlassesDTO;
import com.lirou.store.domain.entities.Glasses;
import com.lirou.store.services.SearchService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/{query}")
    public ResponseEntity<?> searchProduct(@PathVariable String query, @PageableDefault(page = 0, size = 24, direction = Sort.Direction.ASC, sort = { "title" }) Pageable pageable){
        Page<GlassesDTO> result = searchService.searchGlassesWithQuery(query, pageable);
        return ResponseEntity.ok(result);
    }
}
