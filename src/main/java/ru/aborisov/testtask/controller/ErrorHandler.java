package ru.aborisov.testtask.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.aborisov.testtask.exception.UserAlreadyExistsException;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UserAlreadyExistsException handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return ex;
    }
}
