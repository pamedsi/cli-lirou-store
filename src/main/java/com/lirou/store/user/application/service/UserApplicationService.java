package com.lirou.store.user.application.service;

import com.lirou.store.user.application.api.UserDTO;
import com.lirou.store.user.domain.User;
import com.lirou.store.user.infra.UserInfraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserApplicationService implements UserService{
    private final UserInfraRepository userInfraRepository;

    public void createUser(UserDTO userDTO) {
        log.info("[starts] UserApplicationService -> createUser()");
        User newUser = new User(userDTO);
        userInfraRepository.saveUser(newUser);
        log.info("[ends UserApplicationService -> createUser()");
    }
}
