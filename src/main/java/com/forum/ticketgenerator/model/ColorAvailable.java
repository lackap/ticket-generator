package com.forum.ticketgenerator.model;

public enum ColorAvailable {
    GREEN("Vert"),
    YELLOW("Jaune"),
    ORANGE("Orange"),
    RED("Rouge"),
    LIGHT_GRAY("Gris"),
    PINK("Rose"),
    BLUE("Bleu");

    private final String value;

    ColorAvailable(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
