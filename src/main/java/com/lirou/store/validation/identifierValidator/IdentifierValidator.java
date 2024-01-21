package com.lirou.store.validation.identifierValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class IdentifierValidator implements ConstraintValidator<ValidIdentifier, String> {

    // Peguei a REGEX em: https://www.baeldung.com/java-validate-uuid-string
    private static final Pattern UUID_REGEX = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    @Override
    public boolean isValid(String identifier, ConstraintValidatorContext constraintValidatorContext) {
        return UUID_REGEX.matcher(identifier).matches();
    }
}
