package com.example.smartcash.models.enums;

public enum AppConstants {
    AUTH_PREFIX("Bearer "),
    BASE_URL("https://smartcash-engine.herokuapp.com/engine/v1");

    private final String name;

    AppConstants(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
