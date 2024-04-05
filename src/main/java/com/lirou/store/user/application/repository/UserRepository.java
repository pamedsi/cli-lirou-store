package com.lirou.store.user.application.repository;

import com.lirou.store.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository {
    void saveUser(User user);
    User getUserWithEmail(String email);
    Page<User> getAllUsers(Pageable pageable);
}
