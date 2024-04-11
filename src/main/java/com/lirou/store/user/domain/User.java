package com.lirou.store.user.domain;

import com.lirou.store.user.application.api.UserRequestDTO;
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
    @Setter(AccessLevel.NONE)
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
    @Setter(AccessLevel.NONE)
    private String passwordHash;
    @Column
    private Boolean deletedAccount;

    public User(UserRequestDTO userRequestDTO) {
        this.identifier = UUID.randomUUID().toString();
        this.name = userRequestDTO.name();
        this.email = userRequestDTO.email();
        this.passwordHash = hash(userRequestDTO.password());
        this.birthDate = userRequestDTO.birthDate();
        this.CPF = userRequestDTO.CPF().orElse(null);
        this.phoneNumber = userRequestDTO.phoneNumber().orElse(null);
        this.role = UserRole.CLIENT;
        this.deletedAccount = false;
    }

    public long getAge() {
        return ChronoUnit.YEARS.between(this.birthDate, LocalDate.now());
    }

    public void updatePassword(String newPassword) {
        this.passwordHash = hash(newPassword);
    }
}
