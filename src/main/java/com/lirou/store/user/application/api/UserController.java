package com.lirou.store.user.application.api;

import com.lirou.store.models.Message;
import com.lirou.store.user.application.service.UserApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
public class UserController implements UserAPI {
    private final UserApplicationService userApplicationService;

    @Override
    public ResponseEntity<?> createUser(UserRequestDTO userRequestDTO) {
        log.info("[starts] UserController -> createUser()");
        userApplicationService.createUser(userRequestDTO);
        log.info("[ends] UserController -> createUser()");
        return ResponseEntity.status(201).body(new Message("Usuário criado com sucesso!"));
    }

    @Override
    public ResponseEntity<Page<UserDetailsDTO>> getUsers(String token, Pageable pageable) {
        log.info("[starts] UserController -> getUsers()");
        Page<UserDetailsDTO> page = userApplicationService.getUsers(pageable);
        log.info("[ends] UserController -> getUsers()");
        return ResponseEntity.ok(page);
    }

    @Override
    public ResponseEntity<UserDetailsDTO> getUser(String token, String userIdentifier) {
        log.info("[starts] UserController -> getUser()");
        UserDetailsDTO user = userApplicationService.getUser(userIdentifier);
        log.info("[ends] UserController -> getUser()");
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<?> editUser(String token, UserRequestDTO userDTO) {
        log.info("[starts] UserController -> editUser()");
        userApplicationService.editUser(token, userDTO);
        log.info("[ends] UserController -> editUser()");
        return ResponseEntity.accepted().build();
    }
}