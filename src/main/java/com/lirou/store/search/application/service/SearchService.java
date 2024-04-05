package com.lirou.store.search.application.service;

import com.lirou.store.glasses.application.api.GlassesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchService {
    Page<GlassesDTO> searchProductWithQuery(String query, Pageable pageable);
}
