package ru.aborisov.testtask.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.aborisov.testtask.exception.AppSecurityException;
import ru.aborisov.testtask.exception.UserAlreadyExistsException;
import ru.aborisov.testtask.exception.UserNotFoundException;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UserAlreadyExistsException handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return ex;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UserNotFoundException handleUserNotFound(UserNotFoundException ex) {
        return ex;
    }

    @ExceptionHandler(value = {AuthenticationException.class, AccessDeniedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Exception handleSecurityException() {
        return new AppSecurityException("Ошибка авторизации");
    }
}
