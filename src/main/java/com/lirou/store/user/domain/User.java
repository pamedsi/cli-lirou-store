package com.lirou.store.user.domain;

import com.lirou.store.user.application.api.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static com.lirou.store.security.PasswordHashing.hash;

@Table (name = "user_entity")
@Entity
@Data
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column
    private String identifier;
    @Column
    private String name;
    @Column (unique = true)
    private String email;
    @Column (unique = true)
    private String phoneNumber;
    @Column
    private LocalDate birthDate;
    @Column (unique = true)
    private String CPF;
    @Column
    @Enumerated (EnumType.STRING)
    private UserRole role;
    @Column
    @Setter(AccessLevel.NONE)
    private LocalDateTime userSince;
    @Column
    private String passwordHash;

    public User(UserDTO userDTO) {
        this.identifier = UUID.randomUUID().toString();
        this.name = userDTO.name();
        this.email = userDTO.email();
        this.passwordHash = hash(userDTO.password());
        this.birthDate = userDTO.birthDate();
        this.CPF = userDTO.CPF().orElse(null);
        this.role = UserRole.CLIENT;
    }

    public long getAge() {
        return ChronoUnit.DAYS.between(this.birthDate, LocalDate.now());
    }
}
