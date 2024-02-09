package com.lirou.store.domain.entities;

import com.lirou.store.models.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Table
@Entity
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column
    private String name;
    @Column
    private LocalDate birthDate;
    @Column
    @Setter(AccessLevel.NONE)
    private LocalDateTime userSince;
    @Column
    @Setter(AccessLevel.NONE)
    private String CPF;
    @Column
    @Setter
    private UserRole role;

    public long getAge() {
        return ChronoUnit.DAYS.between(this.birthDate, LocalDate.now());
    }
}
