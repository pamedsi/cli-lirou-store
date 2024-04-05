package com.lirou.store.address.infra;

import com.lirou.store.address.domain.UserAddress;
import com.lirou.store.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository <UserAddress, Long> {
    List<UserAddress> findAllByOwnerAndDeletedFalse(User owner);

    Optional<UserAddress> findByIdentifier(String identifier);
}
