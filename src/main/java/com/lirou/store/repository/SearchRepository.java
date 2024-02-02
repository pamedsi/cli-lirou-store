package com.lirou.store.repository;

import java.util.List;

import com.lirou.store.domain.entities.Glasses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
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
                "deleted = false " +
                "ORDER BY title " +
                "LIMIT " + pageable.getPageSize() + " OFFSET " + pageable.getOffset() + ";";

        Query jpaQuery = entityManager.createNativeQuery(query, Glasses.class);

        List<Glasses> glassesList = jpaQuery.getResultList();

        String countQuery = "SELECT COUNT(*) FROM " + schema + ".glasses WHERE " +
                "(title ILIKE '%" + term +"%' OR " +
                "frame ILIKE '%" + term +"%' OR " +
                "color ILIKE '%" + term +"%' OR " +
                "brand ILIKE '%" + term +"%') AND " +
                "deleted = false;";
        Query countJpaQuery = entityManager.createNativeQuery(countQuery);
        long totalCount = ((Number) countJpaQuery.getSingleResult()).longValue();

        return new PageImpl<>(glassesList, pageable, totalCount);
    }
}
