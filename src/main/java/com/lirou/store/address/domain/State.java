package com.lirou.store.address.domain;


public enum State {
    AC("Acre"),
    AL("Alagoas"),
    AP("Amapá"),
    AM("Amazonas"),
    BA("Bahia"),
    CE("Ceará"),
    DF("Distrito Federal"),
    ES("Espírito Santo"),
    GO("Goiás"),
    MA("Maranhão"),
    MT("Mato Grosso"),
    MS("Mato Grosso do Sul"),
    MG("Minas Gerais"),
    PA("Pará"),
    PB("Paraíba"),
    PR("Paraná"),
    PE("Pernambuco"),
    PI("Piauí"),
    RJ("Rio de Janeiro"),
    RN("Rio Grande do Norte"),
    RS("Rio Grande do Sul"),
    RO("Rondônia"),
    RR("Roraima"),
    SC("Santa Catarina"),
    SP("São Paulo"),
    SE("Sergipe"),
    TO("Tocantins");

    private final String fullName;

    State(String nome) {
        this.fullName = nome;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAcronym() {
        return this.name();
    }

    public static State fromAcronym(String acronym) {
        for (State estado : State.values()) {
            if (estado.getAcronym().equals(acronym)) {
                return estado;
            }
        }
        return null;
    }

    public static State fromFullName(String fullName) {
        for (State estado : State.values()) {
            if (estado.getFullName().equalsIgnoreCase(fullName)) {
                return estado;
            }
        }
        return null;
    }

}