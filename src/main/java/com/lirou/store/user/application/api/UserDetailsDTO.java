package com.lirou.store.user.application.api;

import com.lirou.store.user.domain.User;
import com.lirou.store.user.domain.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public record UserDetailsDTO (
        String identifier,
        String name,
        String email,
        Long age,
        String CPF,
        UserRole role
) {
    public UserDetailsDTO (User userEntity) {
        this(
                userEntity.getIdentifier(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getAge(),
                userEntity.getCPF(),
                userEntity.getRole()
        );
    }
    public static List<UserDetailsDTO> severalToDTO(List<User> glassesEntities) {
        return glassesEntities.stream().map(UserDetailsDTO::new).collect(Collectors.toList());
    }
    public static Page<UserDetailsDTO> toPageDTO(Page<User> page) {
        List<UserDetailsDTO> list = severalToDTO(page.getContent());
        return new PageImpl<>(list, page.getPageable(), page.getTotalElements());
    }
}
