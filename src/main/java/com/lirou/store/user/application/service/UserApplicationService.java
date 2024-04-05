package com.lirou.store.user.application.service;

import com.lirou.store.user.application.api.NewUserRequestDTO;
import com.lirou.store.user.application.api.UserDetailsDTO;
import com.lirou.store.user.domain.User;
import com.lirou.store.user.infra.UserInfraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserApplicationService implements UserService {
    private final UserInfraRepository userInfraRepository;

    public void createUser(NewUserRequestDTO newUserRequestDTO) {
        log.info("[starts] UserApplicationService -> createUser()");
        User newUser = new User(newUserRequestDTO);
        userInfraRepository.saveUser(newUser);
        log.info("[ends] UserApplicationService -> createUser()");
    }

    @Override
    public Page<UserDetailsDTO> getUsers(Pageable pageable) {
        log.info("[starts] UserApplicationService -> getUsers()");
        Page<User> users = userInfraRepository.getAllUsers(pageable);
        Page<UserDetailsDTO> usersDTO = UserDetailsDTO.toPageDTO(users);
        log.info("[ends] UserApplicationService -> getUsers()");
        return usersDTO;
    }
}
