package com.lirou.store.user.application.service;

import com.lirou.store.user.application.api.NewUserRequestDTO;
import com.lirou.store.user.application.api.UserDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    void createUser(NewUserRequestDTO newUserRequestDTO);
    Page<UserDetailsDTO> getUsers(Pageable pageable);
}
