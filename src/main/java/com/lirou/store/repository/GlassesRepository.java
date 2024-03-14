package com.lirou.store.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.lirou.store.domain.entities.Glasses;

@Repository
public interface GlassesRepository extends JpaRepository<Glasses, Long>, PagingAndSortingRepository<Glasses, Long> {

    Page<Glasses> findAllByDeletedFalse(Pageable pageable);
    Boolean existsByTitleAndIdentifierNotAndDeletedFalse(String title, String identifier);
    Boolean existsByTitleAndDeletedFalse(String title);
    Optional<Glasses> findByIdentifierAndDeletedFalse(String glassesIdentifier);
}
