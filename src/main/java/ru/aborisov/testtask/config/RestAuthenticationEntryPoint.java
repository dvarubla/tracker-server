package ru.aborisov.testtask.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RestAuthenticationEntryPoint implements AuthExceptionProcessor {

    private final HandlerExceptionResolver resolver;

    @Autowired
    public RestAuthenticationEntryPoint(HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        resolver.resolveException(request, response, null, exception);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) {
        resolver.resolveException(request, response, null, exception);
    }
}
