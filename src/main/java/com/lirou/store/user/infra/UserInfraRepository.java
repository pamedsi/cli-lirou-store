package com.lirou.store.user.infra;

import com.lirou.store.handler.exceptions.NotFoundException;
import com.lirou.store.user.application.repository.UserRepository;
import com.lirou.store.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
@RequiredArgsConstructor
public class UserInfraRepository implements UserRepository {
    private final UserJPARepository userJPARepository;

    @Override
    public User getUserWithEmail(String email)  {
        log.info("[starts] UserInfraRepository - getUserWithEmail()");
        User user = userJPARepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Usuário não encontrado!"));
        log.info("[ends] UserInfraRepository - getUserWithEmail()");
        return user;
    }

    @Override
    public void saveUser(User user) {
        log.info("[starts] UserInfraRepository - saveUser()");
        userJPARepository.save(user);
        log.info("[ends] UserInfraRepository - saveUser()");
    }
}
