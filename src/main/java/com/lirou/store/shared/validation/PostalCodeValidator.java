package com.lirou.store.shared.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostalCodeValidator {
    // Peguei essa regex no Fremework Demoiselle, mexi um pouco no código, mas o original está na URL abaixo:
    //    https://github.com/demoiselle/validation/blob/master/impl/src/main/java/br/gov/frameworkdemoiselle/validation/internal/validator/CepValidator.java

    public static boolean isAValidePostalCode(String cep) {
        if (cep == null || cep.isEmpty()) return false;

        Pattern pattern = Pattern.compile("^(([0-9]{2}\\.[0-9]{3}-[0-9]{3})|([0-9]{2}[0-9]{3}-[0-9]{3})|([0-9]{8}))$");
        Matcher matcher = pattern.matcher(cep);
        return matcher.find();
    }
}
