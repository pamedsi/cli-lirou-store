package com.lirou.store.user.application.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public interface UserAPI {

    @PostMapping
    ResponseEntity<?> createUser(@RequestBody UserDTO userDTO);
}
