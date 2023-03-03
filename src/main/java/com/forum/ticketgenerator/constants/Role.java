package com.forum.ticketgenerator.constants;

public enum Role {
    USER("Utilisateur"),
    ENTREPRISE("Entreprise"),
    FORMATION("Formation"),
    ADMIN("Administrateur");

    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue () {
        return value;
    }
}
