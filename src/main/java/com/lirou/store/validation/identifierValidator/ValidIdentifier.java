package com.lirou.store.validation.identifierValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdentifierValidator.class)
public @interface ValidIdentifier {
    String message() default "Identificador inválido!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
