package ru.aborisov.testtask.resource;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class User {
    @JsonUnwrapped
    private Credentials credentials;
    private String name;

    public User() {
    }

    public User(Credentials credentials, String name) {
        this.credentials = credentials;
        this.name = name;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
