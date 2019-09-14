package ru.aborisov.testtask.config;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

public interface AuthExceptionProcessor extends AuthenticationEntryPoint, AccessDeniedHandler {
}
