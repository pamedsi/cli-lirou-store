package com.lirou.store.services;

import com.lirou.store.domain.DTOs.UserDTO;
import com.lirou.store.domain.entities.UserEntity;
import com.lirou.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {
    private final UserRepository userRepository;

    public void createUser(UserDTO userDTO) {
        UserEntity newUser = new UserEntity(userDTO);
        log.info("[inicia] UserRepository -> save()");
        userRepository.save(newUser);
        log.info("[finaliza] UserRepository -> save()");
    }
}
