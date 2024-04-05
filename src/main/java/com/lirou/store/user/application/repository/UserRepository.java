package com.lirou.store.user.application.repository;

import com.lirou.store.user.domain.User;

public interface UserRepository {
    void saveUser(User user);
    User getUserWithEmail(String email);
}
