package com.lirou.store.repository;

import com.lirou.store.domain.entities.Glasses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchesRepository extends JpaRepository<Glasses, Long> {
    Page<Glasses> findByTitleContainingIgnoreCaseOrModelContainingIgnoreCaseOrFrameContainingIgnoreCaseOrColorContainingIgnoreCaseOrBrandContainingIgnoreCase(String title, String model, String frame, String color, String brand, Pageable pageable);
}
