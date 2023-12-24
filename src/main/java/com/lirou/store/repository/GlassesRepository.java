package com.lirou.store.repository;

import com.lirou.store.entities.Glasses;
import com.lirou.store.services.GlassesService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlassesRepository extends JpaRepository<Glasses, Long> {

    List<Glasses> findAllByDeletedFalse();

    Glasses findByIdentifierAndDeletedFalse(String glassesIdentifier);
}
