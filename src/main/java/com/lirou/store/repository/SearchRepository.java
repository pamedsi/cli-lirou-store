package com.lirou.store.repository;

import com.lirou.store.domain.entities.Glasses;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.EntityManager;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SearchRepository {
    private final EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    @Value("${DATABASE_SCHEMA}")
    private String schema;

    @PostConstruct
    public void instantiateCriteriaBuilder() {
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

//    public Page<Glasses> searchGlasses(String term, Pageable pageable) {
//        String query = "SELECT * FROM " + schema + ".glasses WHERE " +
//                "(title ILIKE '%" + term +"%' OR " +
//                "frame ILIKE '%" + term +"%' OR " +
//                "color ILIKE '%" + term +"%' OR " +
//                "brand ILIKE '%" + term +"%') AND " +
//                "deleted = false " +
//                "ORDER BY title " +
//                "LIMIT " + pageable.getPageSize() + " OFFSET " + pageable.getOffset() + ";";
//
//        Query jpaQuery = entityManager.createNativeQuery(query, Glasses.class);
//
//        List<Glasses> glassesList = jpaQuery.getResultList();
//
//        String countQuery = "SELECT COUNT(*) FROM " + schema + ".glasses WHERE " +
//                "(title ILIKE '%" + term +"%' OR " +
//                "frame ILIKE '%" + term +"%' OR " +
//                "color ILIKE '%" + term +"%' OR " +
//                "brand ILIKE '%" + term +"%') AND " +
//                "deleted = false;";
//        Query countJpaQuery = entityManager.createNativeQuery(countQuery);
//        long totalCount = ((Number) countJpaQuery.getSingleResult()).longValue();
//
//        return new PageImpl<>(glassesList, pageable, totalCount);
//    }

    public Page<Glasses> searchGlasses(String term, Pageable pageable) {
        CriteriaQuery<Glasses> criteriaQuery = criteriaBuilder.createQuery(Glasses.class);
        Root<Glasses> root = criteriaQuery.from(Glasses.class);
        Predicate queryPredicate = getGlassesPredicate(root, term);

        criteriaQuery.where(queryPredicate);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("title")));
        TypedQuery<Glasses> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        Long glassesCount = getSearchCount(queryPredicate);
        return new PageImpl<>(query.getResultList(), pageable, glassesCount);
    }

    private Long getSearchCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Glasses> countRoot = countQuery.from(Glasses.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Predicate getGlassesPredicate(Root<Glasses> root, String term){
        Predicate titlePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + term.toLowerCase() + "%");
        Predicate framePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("frame")), "%" + term.toLowerCase() + "%");
        Predicate colorPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("color")), "%" + term.toLowerCase() + "%");
        Predicate brandPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("brand")), "%" + term.toLowerCase() + "%");
        Predicate deletedPredicate = criteriaBuilder.equal(root.get("deleted"), false);
        Predicate orPredicate = criteriaBuilder.or(titlePredicate, framePredicate, colorPredicate, brandPredicate);
        return criteriaBuilder.and(orPredicate, deletedPredicate);
    }
}
