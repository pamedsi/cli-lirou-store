package com.lirou.store.user.application.api;

import com.lirou.store.models.Message;
import com.lirou.store.user.application.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/api/user")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO){
        log.info("[inicia] UserService -> createUser()");
        userService.createUser(userDTO);
        log.info("[finaliza] UserService -> createUser()");
        return ResponseEntity.status(201).body(new Message("Usuário criado com sucesso!"));
    }

}
