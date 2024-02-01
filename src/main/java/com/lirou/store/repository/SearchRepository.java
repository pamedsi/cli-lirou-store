package com.lirou.store.repository;

import java.util.List;

import com.lirou.store.domain.entities.Glasses;
import org.springframework.beans.factory.annotation.Value;
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

    public List<Glasses> searchGlasses(String term) {
        String query = "SELECT * FROM " + schema + ".glasses WHERE " +
                "(title ILIKE '%" + term +"%' OR " +
                "frame ILIKE '%" + term +"%' OR " +
                "color ILIKE '%" + term +"%' OR " +
                "brand ILIKE '%" + term +"%') AND " +
                "deleted = false;";

        Query jpaQuery = entityManager.createNativeQuery(query, Glasses.class);
        return jpaQuery.getResultList();
    }

//    public Page<Glasses> searchGlasses(String term, Pageable pageable) {
//           String query = "SELECT * FROM " + schema + ".glasses WHERE " +
//                "(title ILIKE '%" + term + "%' OR " +
//                "frame ILIKE '%" + term + "%' OR " +
//                "color ILIKE '%" + term + "%' OR " +
//                "brand ILIKE '%" + term + "%') AND " +
//                "deleted = false;";

//        Query jpaQuery = entityManager.createQuery(query, Glasses.class);
//        jpaQuery.setParameter("term", "%" + term +"%");
//        return (Page<Glasses>) jpaQuery.setFirstResult((int) pageable.getOffset())
//                .setMaxResults(pageable.getPageSize())
//                .getResultStream().collect(Collectors.toList());
//    }

}
