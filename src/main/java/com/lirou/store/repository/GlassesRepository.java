package com.lirou.store.repository;

import com.lirou.store.entities.Glasses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlassesRepository extends JpaRepository<Long, Glasses> {
}
