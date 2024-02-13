package com.lirou.store.models;


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

    public String getSigla() {
        return this.name();
    }

    public static State fromAcronym(String sigla) {
        for (State estado : State.values()) {
            if (estado.getSigla().equals(sigla)) {
                return estado;
            }
        }
        return null;
    }

}