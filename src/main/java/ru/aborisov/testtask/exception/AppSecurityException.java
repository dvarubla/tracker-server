package ru.aborisov.testtask.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@JsonAutoDetect(fieldVisibility = NONE, getterVisibility = NONE, setterVisibility = NONE)

public class AppSecurityException extends Exception {
    public AppSecurityException() {
    }

    public AppSecurityException(String message) {
        super(message);
    }

    public AppSecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    @JsonProperty
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
