package com.lirou.store.search.infra;


import com.lirou.store.glasses.domain.Glasses;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.EntityManager;
import jakarta.annotation.PostConstruct;

import jakarta.persistence.criteria.Root;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Log4j2
public class GlassesSearchRepository {
    private final EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    @PostConstruct
    public void instantiateCriteriaBuilder() {
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Glasses> searchGlasses(String term, Pageable pageable) {
        CriteriaQuery<Glasses> criteriaQuery = criteriaBuilder.createQuery(Glasses.class);
        Root<Glasses> root = criteriaQuery.from(Glasses.class);
        Predicate queryPredicate = getSearchGlassesPredicate(root, term);

        criteriaQuery.where(queryPredicate);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("title")));
        TypedQuery<Glasses> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        Long glassesCount = getSearchGlassesCount(term);
        return new PageImpl<>(query.getResultList(), pageable, glassesCount);
    }

    private Long getSearchGlassesCount(String term) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Glasses> countRoot = countQuery.from(Glasses.class);
        Predicate predicate = getSearchGlassesPredicate(countRoot, term);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Predicate getSearchGlassesPredicate(Root<Glasses> root, String term){
        Predicate titlePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + term.toLowerCase() + "%");
        Predicate framePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("frame")), "%" + term.toLowerCase() + "%");
        Predicate colorPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("color")), "%" + term.toLowerCase() + "%");
        Predicate brandPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("brand")), "%" + term.toLowerCase() + "%");
        Predicate deletedPredicate = criteriaBuilder.equal(root.get("deleted"), false);
        Predicate orPredicate = criteriaBuilder.or(titlePredicate, framePredicate, colorPredicate, brandPredicate);
        return criteriaBuilder.and(orPredicate, deletedPredicate);
    }
}
