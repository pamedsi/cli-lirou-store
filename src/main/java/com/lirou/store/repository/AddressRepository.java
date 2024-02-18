package com.lirou.store.repository;

import com.lirou.store.domain.entities.AddressEntity;
import com.lirou.store.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository <AddressEntity, Long> {
    List<AddressEntity> findAllByOwnerAndDeletedFalse(User owner);

    Optional<AddressEntity> findByIdentifier(String identifier);
}
