package ru.aborisov.testtask.config;

public enum RoleAlias {
    ADMIN("admin"),
    MANAGER("manager"),
    USER("user"),
    NOBODY("nobody");

    private String alias;

    RoleAlias(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
}
