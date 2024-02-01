package com.lirou.store.services;

import com.lirou.store.domain.DTOs.GlassesDTO;
import com.lirou.store.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;

    public Page<GlassesDTO> searchGlassesWithQuery(String query, Pageable pageable) {
          return GlassesDTO.toPageDTO(searchRepository.searchGlasses(query, pageable));
    }
}
