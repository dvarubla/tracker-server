package ru.aborisov.testtask.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@JsonAutoDetect(fieldVisibility = NONE, getterVisibility = NONE, setterVisibility = NONE)
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException() {
    }

    public UserAlreadyExistsException(String login) {
        super(login);
    }

    public UserAlreadyExistsException(String login, Throwable cause) {
        super(login, cause);
    }

    @JsonProperty
    @Override
    public String getMessage() {
        return "Пользователь с логином " + super.getMessage() + " уже существует";
    }
}
