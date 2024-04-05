package com.lirou.store.user.application.service;

import com.lirou.store.user.application.api.UserDTO;
import com.lirou.store.user.domain.User;
import com.lirou.store.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {
    private final UserRepository userRepository;

    public void createUser(UserDTO userDTO) {
        User newUser = new User(userDTO);
        log.info("[inicia] UserRepository -> save()");
        userRepository.save(newUser);
        log.info("[finaliza] UserRepository -> save()");
    }
}
