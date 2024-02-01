package com.lirou.store.services;

import com.lirou.store.domain.entities.Glasses;
import com.lirou.store.repository.SearchRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;


    public List<Glasses> searchGlassesWithQuery(String query, Optional<Integer> page) {
//        return searchRepository.searchGlasses(query, PageRequest.of(page.orElse(0), 24));
          return searchRepository.searchGlasses(query);
    }
}
