package com.lirou.store.controllers;

import com.lirou.store.entities.Glasses;
import com.lirou.store.services.SearchService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/{query}")
    public ResponseEntity<?> searchProduct(@PathVariable String query, @RequestParam Optional<Integer> page){
        List<Glasses> result = searchService.searchGlassesWithQuery(query, page);
        return ResponseEntity.ok(result);
    }
}
