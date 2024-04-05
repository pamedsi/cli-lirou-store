package com.lirou.store.glasses.infra;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.lirou.store.glasses.domain.Glasses;

@Repository
public interface GlassesJPARepository extends JpaRepository<Glasses, Long>, PagingAndSortingRepository<Glasses, Long> {
    Page<Glasses> findAllByDeletedFalse(Pageable pageable);
    Boolean existsByTitleAndDeletedFalse(String title);
    Optional<Glasses> findByIdentifierAndDeletedFalse(String glassesIdentifier);
}
