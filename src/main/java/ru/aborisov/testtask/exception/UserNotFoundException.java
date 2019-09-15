package ru.aborisov.testtask.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@JsonAutoDetect(fieldVisibility = NONE, getterVisibility = NONE, setterVisibility = NONE)
public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
    }

    public UserNotFoundException(int id) {
        super(String.valueOf(id));
    }

    public UserNotFoundException(int id, Throwable cause) {
        super(String.valueOf(id), cause);
    }

    @JsonProperty
    @Override
    public String getMessage() {
        return "Пользователь с id " + super.getMessage() + " не существует";
    }
}