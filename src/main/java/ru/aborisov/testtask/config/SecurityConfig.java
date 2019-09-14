package ru.aborisov.testtask.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AnonAuthoritiesObtainer anonAuthoritiesObtainer;

    private final AuthExceptionProcessor entryPoint;

    @Autowired
    public SecurityConfig(
            AnonAuthoritiesObtainer anonAuthoritiesObtainer,
            AuthExceptionProcessor entryPoint
    ) {
        this.anonAuthoritiesObtainer = anonAuthoritiesObtainer;
        this.entryPoint = entryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler(entryPoint)
                .authenticationEntryPoint(entryPoint)
                .and()
                .anonymous().principal(RoleAlias.NOBODY).authorities(anonAuthoritiesObtainer.getAuthorities())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.DELETE, "/api/user").hasAuthority(PrivilegeAlias.MANAGE_USERS.getAlias())
                .antMatchers(HttpMethod.GET, "/api/users").hasAuthority(PrivilegeAlias.MANAGE_USERS.getAlias())
                .antMatchers(HttpMethod.POST, "/api/user").hasAuthority(PrivilegeAlias.CREATE_USER.getAlias())
                .and().httpBasic().authenticationEntryPoint(entryPoint)
        ;

    }
}