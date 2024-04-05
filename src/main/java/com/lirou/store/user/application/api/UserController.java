package com.lirou.store.user.application.api;

import com.lirou.store.models.Message;
import com.lirou.store.user.application.service.UserApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;

@Log4j2
@RequiredArgsConstructor
public class UserController implements UserAPI{
    private final UserApplicationService userApplicationService;

    @Override
    public ResponseEntity<?> createUser(UserDTO userDTO) {
        log.info("[inicia] UserController -> createUser()");
        userApplicationService.createUser(userDTO);
        log.info("[finaliza] UserController -> createUser()");
        return ResponseEntity.status(201).body(new Message("Usuário criado com sucesso!"));
    }
}