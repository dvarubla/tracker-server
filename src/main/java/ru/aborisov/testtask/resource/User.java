package ru.aborisov.testtask.resource;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;

public class User {
    @JsonUnwrapped
    @Valid
    private Credentials credentials;
    @NotBlank(message = "Имя не может быть пустым")
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
