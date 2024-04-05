package com.lirou.store.user.application.service;

import com.lirou.store.user.application.api.UserRequestDTO;
import com.lirou.store.user.application.api.UserDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    void createUser(UserRequestDTO userRequestDTO);
    Page<UserDetailsDTO> getUsers(Pageable pageable);
    UserDetailsDTO getUser(String userIdentifier);
    void editUser(String token, UserRequestDTO userDTO);
    void deleteUser(String token, String userIdentifier);
}
