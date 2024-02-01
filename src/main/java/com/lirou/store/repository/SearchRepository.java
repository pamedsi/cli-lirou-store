package com.lirou.store.repository;

import java.util.List;
import java.util.stream.Collectors;

import com.lirou.store.domain.entities.Glasses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SearchRepository {
    private final EntityManager entityManager;
    @Value("${DATABASE_SCHEMA}")
    private String schema;

    public Page<Glasses> searchGlasses(String term, Pageable pageable) {
        String query = "SELECT * FROM " + schema + ".glasses WHERE " +
                "(title ILIKE '%" + term +"%' OR " +
                "frame ILIKE '%" + term +"%' OR " +
                "color ILIKE '%" + term +"%' OR " +
                "brand ILIKE '%" + term +"%') AND " +
                "deleted = false;";

        Query jpaQuery = entityManager.createNativeQuery(query, Glasses.class);
        List<Glasses> glassesInList = jpaQuery.getResultList();


        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), glassesInList.size());

        List<Glasses> pageContent = glassesInList.subList(start, end);

        return new PageImpl<>(pageContent, pageable, glassesInList.size());
    }

}
