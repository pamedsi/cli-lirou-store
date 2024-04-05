package com.lirou.store.user.application.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public interface UserAPI {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<?> createUser(@RequestBody NewUserRequestDTO newUserRequestDTO);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Page<UserDetailsDTO>> getUsers(@RequestHeader("Authorization") String token, @PageableDefault(direction = Sort.Direction.ASC, sort = { "name" }) Pageable pageable);

    @GetMapping("/{identifier}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<UserDetailsDTO> getUser(@RequestHeader("Authorization") String token, @PathVariable("identifier") String userIdentifier);
}
