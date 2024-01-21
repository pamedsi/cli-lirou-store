package com.lirou.store.repository;

import com.lirou.store.entities.Glasses;
import com.lirou.store.services.GlassesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlassesRepository extends JpaRepository<Glasses, Long>, PagingAndSortingRepository<Glasses, Long> {

    Page<Glasses> findAllByDeletedFalse(Pageable pageable);
    List<Glasses> getAllByDeletedFalse();

    Glasses findByIdentifierAndDeletedFalse(String glassesIdentifier);
}
