package ru.aborisov.testtask.config;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface AnonAuthoritiesObtainer {
    List<GrantedAuthority> getAuthorities();
}
