package com.lirou.store.search.application.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/search")
public interface SearchAPI {

    @GetMapping
    ResponseEntity<?> searchProduct(@RequestParam(value = "query", required = false, defaultValue = "") String query, @PageableDefault(size = 24) Pageable pageable);
}
