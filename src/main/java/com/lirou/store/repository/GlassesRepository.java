package com.lirou.store.repository;

import com.lirou.store.entities.Glasses;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlassesRepository extends JpaRepository<Glasses, Long>, PagingAndSortingRepository<Glasses, Long> {

    Page<Glasses> findAllByDeletedFalse(Pageable pageable);
    Boolean existsByTitleAndIdentifierNotAndDeletedFalse(String title, String identifier);
    Boolean existsByTitleAndDeletedFalse(String title);
    Glasses findByIdentifierAndDeletedFalse(String glassesIdentifier);
}
