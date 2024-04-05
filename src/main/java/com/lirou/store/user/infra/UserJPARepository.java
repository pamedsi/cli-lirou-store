package com.lirou.store.user.infra;

import com.lirou.store.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJPARepository extends JpaRepository <User, Long> {
    Optional<User> findByEmail(String email);
    Page<User> findAllByDeletedAccountFalse(Pageable pageable);
    Optional<User> findByIdentifierAndDeletedAccountFalse(String userIdentifier);
}
