package com.lirou.store.validation;

import java.util.UUID;
import java.util.regex.Pattern;

public class IdentifierValidator {

    // Peguei a REGEX em: https://www.baeldung.com/java-validate-uuid-string
    private static final Pattern UUID_REGEX = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    public static boolean validIdentifier(String identifier) {
        return UUID_REGEX.matcher(identifier).matches();
    }
}
