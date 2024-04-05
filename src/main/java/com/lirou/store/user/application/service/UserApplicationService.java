package com.lirou.store.user.application.service;

import com.lirou.store.security.TokenService;
import com.lirou.store.user.application.api.UserRequestDTO;
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
    private final TokenService tokenService;

    public void createUser(UserRequestDTO userRequestDTO) {
        log.info("[starts] UserApplicationService -> createUser()");
        User newUser = new User(userRequestDTO);
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
    @Override
    public UserDetailsDTO getUser(String userIdentifier) {
        log.info("[starts] UserApplicationService -> getUser()");
        User user = userInfraRepository.getUserWithIdentifier(userIdentifier);
        UserDetailsDTO userDTO = new UserDetailsDTO(user);
        log.info("[ends] UserApplicationService -> getUser()");
        return userDTO;
    }
    @Override
    public void editUser(String token, UserRequestDTO userDTO) {
        log.info("[starts] UserApplicationService -> editUser()");
        String email = tokenService.decode(token);
        User user = userInfraRepository.getUserWithEmail(email);
        updateUserFromDTO(user, userDTO);
        userInfraRepository.saveUser(user);
        log.info("[ends] UserApplicationService -> editUser()");
    }
    private void updateUserFromDTO(User user, UserRequestDTO userDTO) {
        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        user.setPhoneNumber(userDTO.phoneNumber().orElse(null));
        user.setBirthDate(userDTO.birthDate());
        user.setCPF(userDTO.CPF().orElse(null));
        user.updatePassword(userDTO.password());
    }
}
