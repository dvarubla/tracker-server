package ru.aborisov.testtask.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@JsonAutoDetect(fieldVisibility = NONE, getterVisibility = NONE, setterVisibility = NONE)
public class UserNotFoundException extends Exception {
    private boolean hasLogin;
    public UserNotFoundException() {
        hasLogin = true;
    }

    public UserNotFoundException(String login) {
        super(login);
        hasLogin = true;
    }

    public UserNotFoundException(String login, Throwable cause) {
        super(login, cause);
        hasLogin = true;
    }

    public UserNotFoundException(int id) {
        super(String.valueOf(id));
        hasLogin = false;
    }

    public UserNotFoundException(int id, Throwable cause) {
        super(String.valueOf(id), cause);
        hasLogin = false;
    }

    @JsonProperty
    @Override
    public String getMessage() {
        return (hasLogin)
                ? "Пользователь с логином " + super.getMessage() + " не существует"
                : "Пользователь с id " + super.getMessage() + " не существует"
                ;
    }
}