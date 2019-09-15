package ru.aborisov.testtask.controller;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.aborisov.testtask.exception.AppSecurityException;
import ru.aborisov.testtask.exception.ExpenseNotFoundException;
import ru.aborisov.testtask.exception.UserAlreadyExistsException;
import ru.aborisov.testtask.exception.UserNotFoundException;
import ru.aborisov.testtask.exception.ValidationException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler({
            UserNotFoundException.class, UserAlreadyExistsException.class,
            ValidationException.class, ExpenseNotFoundException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Exception handleBadRequestException(Exception ex) {
        return ex;
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class, org.springframework.validation.BindException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Exception handleInvalidJSONException() {
        return new ValidationException("Неверный формат данных");
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

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ValidationException handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return new ValidationException(String.join(", ",ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList())));

    }
}
