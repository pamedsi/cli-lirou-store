package com.lirou.store.repository;

import com.lirou.store.domain.entities.AddressEntity;
import com.lirou.store.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository <AddressEntity, Long> {
    List<AddressEntity> findAllByOwner(UserEntity owner);
}
