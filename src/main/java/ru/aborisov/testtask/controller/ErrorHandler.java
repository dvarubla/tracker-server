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
import ru.aborisov.testtask.exception.ValidationException;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler({UserNotFoundException.class, UserAlreadyExistsException.class, ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Exception handleBadRequestException(Exception ex) {
        return ex;
    }

    @ExceptionHandler(value = {AuthenticationException.class, AccessDeniedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Exception handleSecurityException() {
        return new AppSecurityException("Ошибка авторизации");
    }

    @ExceptionHandler(value = {AppSecurityException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Exception handleSecurityException(Exception ex) {
        return ex;
    }
}
