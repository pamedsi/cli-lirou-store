package com.lirou.store.domain.entities;

import com.lirou.store.Enums.UserRole;
import com.lirou.store.domain.DTOs.UserDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column
    private String name;
    @Column (unique = true)
    private String email;
    @Column
    private LocalDate birthDate;
    @Column (unique = true)
    private String CPF;
    @Column
    @Setter
    @Enumerated (EnumType.STRING)
    private UserRole role;
    @Column
    @Setter(AccessLevel.NONE)
    private LocalDateTime userSince;

    public UserEntity(UserDTO userDTO) {
        this.name = userDTO.name();
        this.email = userDTO.email();
        this.birthDate = userDTO.birthDate();
        this.CPF = userDTO.CPF().orElse(null);
        this.role = UserRole.CLIENT;
    }

    public long getAge() {
        return ChronoUnit.DAYS.between(this.birthDate, LocalDate.now());
    }
}
